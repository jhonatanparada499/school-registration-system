package com.school.app.controller;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Map;
import com.school.app.model.Course;
import com.school.app.model.Instructor;
import com.school.app.model.Student;
import com.school.app.service.*;
import com.school.app.model.Classroom;
import com.school.app.model.ClassSession;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.Collectors;

import javafx.scene.control.Alert;

/**
 * Methods:
 * 1. createClassSection
 * 2. registerStudent
 */

public class AdministrationSceneController {
  @FXML
  private Button BCreate;
  @FXML
  private ComboBox<String> CBCourseId;
  @FXML
  private ComboBox<String> CBInstructorId;
  @FXML
  private ComboBox<String> CBClassroomId;
  @FXML
  private TextField TFCapacity;
  @FXML
  private ComboBox<String> CBStudentId;
  @FXML
  private ComboBox<String> CBClassSectionId;
  @FXML
  private Button BRegister;

  // custom class
  FilterableComboBox CBClassSection;
  FilterableComboBox CBCourse;
  FilterableComboBox CBInstructor;

  private Alert infoAlert;
  private Alert errorAlert;

  private ObservableList<String> courseIds;
  private ObservableList<String> studentIds;
  private ObservableList<String> classroomIds;
  private ObservableList<String> instructorIds;
  private ObservableList<String> elegibleInstructorIds;
  private ObservableList<String> classSectionIds;

  private Map<Integer, ClassSession> classSections;
  private Map<String, Course> courses;
  private Map<String, Classroom> classrooms;
  private Map<String, Student> students;
  private Map<String, Instructor> instructors;

  RegistrationService registrationService;

  public AdministrationSceneController(
      Map<Integer, ClassSession> theClassSections,
      Map<String, Course> theCourses,
      Map<String, Classroom> theClassrooms,
      Map<String, Student> theStudents,
      Map<String, Instructor> theInstructors,

      RegistrationService theRegistrationService) {

    classSections = theClassSections;
    courses = theCourses;
    classrooms = theClassrooms;
    students = theStudents;
    instructors = theInstructors;

    registrationService = theRegistrationService;
    // get class section ids
    classSectionIds = getClassSectionIds(classSections);

    // get course ids
    courseIds = FXCollections.observableArrayList(
        courses.values().stream().map(Course::getCourseId).collect(Collectors.toList()));

    // get classroom ids
    classroomIds = FXCollections.observableArrayList(
        classrooms.values().stream().map(Classroom::getRoomNumber).collect(Collectors.toList()));

    // get student ids
    studentIds = FXCollections.observableArrayList(
        students.values().stream().map(Student::getId).collect(Collectors.toList()));

    // get instructor ids
    instructorIds = FXCollections.observableArrayList(
        instructors.values().stream().map(Instructor::getId).collect(Collectors.toList()));
  }

  @FXML
  public void initialize() {
    CBCourseId.setVisibleRowCount(5);
    CBInstructorId.setVisibleRowCount(5);
    CBClassroomId.setVisibleRowCount(5);
    CBStudentId.setVisibleRowCount(5);
    CBClassSectionId.setVisibleRowCount(5);

    // CBCourse and CBInstructor are dynamic and related with each other, that is
    // why I need to Id them
    CBCourse = new FilterableComboBox(CBCourseId, courseIds);
    CBInstructor = new FilterableComboBox(CBInstructorId, instructorIds);
    // CBClassSection items must be updated when new class section is created
    CBClassSection = new FilterableComboBox(CBClassSectionId, classSectionIds);

    // CBClassroomId and CBStudentId are static and independent, that is way I don't
    // id them
    new FilterableComboBox(CBClassroomId, classroomIds);
    new FilterableComboBox(CBStudentId, studentIds);

    // attach listener to when the combobox dropdown is displayed and hidden
    CBCourse.getComboBox().showingProperty().addListener((obs, wasShowing, isNowShowing) -> {
      if (wasShowing) {

        // precondition
        if (CBCourseId.getValue() == null) {
          return;
        }

        // filter to get instructor that can teach course
        String courseId = CBCourseId.getValue().trim().toUpperCase();

        // preconditions for courseId value in comboboxe
        if (courseId.trim().isEmpty()) {
          return;
        }

        Course course = courses.get(courseId);

        // precondition for course object
        if (course == null) {
          return;
        }

        List<Instructor> elegibleInstructors = registrationService.findEligibleInstructors(
            course);

        // precondition for elegibleInstructors
        if (elegibleInstructors == null || elegibleInstructors.isEmpty()) {
          return;
        }
        elegibleInstructorIds = getElegibleInstructorIds(
            elegibleInstructors);
        // CBInstructorId.getEditor().clear();

        // update Instructor Combobox wiht new instructor ids
        CBInstructor.setFilteredItems(elegibleInstructorIds);
      }
    });

    // initialize alert windows
    String title = "School Registration System";
    infoAlert = new Alert(Alert.AlertType.INFORMATION);
    infoAlert.setTitle(title);

    errorAlert = new Alert(Alert.AlertType.ERROR);
    errorAlert.setTitle(title);
  }

  @FXML
  private void createClassSection(ActionEvent event) {
    // in case user enters lowercase
    String courseId = CBCourseId.getValue().trim().toUpperCase();
    if (courses.get(courseId) == null) {
      errorAlert.setContentText("Invalid course ID.");
      errorAlert.showAndWait();
      return;
    }

    String instructorId = CBInstructorId.getValue().trim().toUpperCase();

    if (instructors.get(instructorId) == null) {
      errorAlert.setContentText("Invalid instructor ID.");
      errorAlert.showAndWait();
      return;
    }

    String classroomId = CBClassroomId.getValue().trim().toUpperCase();

    if (classrooms.get(classroomId) == null) {
      errorAlert.setContentText("Invalid classroom ID.");
      errorAlert.showAndWait();
      return;
    }

    int capacity = 0;
    try {
      capacity = Integer.parseInt(TFCapacity.getText().trim());
      if (capacity < 0) {
        errorAlert.setContentText("Capacity cannot be negative.");
        errorAlert.showAndWait();
        return;
      }
    } catch (Exception e) {
      errorAlert.setContentText("Invalid capacity");
      errorAlert.showAndWait();
      return;
    }

    try {
      ClassSession newClassSection = registrationService.createClassSection(
          courseId,
          instructorId,
          classroomId,
          capacity);

      instructors.get(instructorId).addTeachingAssignment(newClassSection);
      // classSections points to the global classesctions Map
      classSections.put(
          newClassSection.getId(), newClassSection);

      // no need to clean items here, FilterableComboBox does it
      CBClassSection.setFilteredItems(
          getClassSectionIds(classSections));

      // write new class sectio to csv file
      // RegistrationService.saveClassSection(newClassSection);

    } catch (Exception e) {
      errorAlert.setContentText(e.getMessage());
      errorAlert.showAndWait();
      return;
    }

    infoAlert.setContentText("Class Section was created successfully.");
    infoAlert.showAndWait();
  }

  @FXML
  void registerStudent(ActionEvent event) {
    String studentId = CBStudentId.getValue().trim();

    if (students.get(studentId) == null) {
      errorAlert.setContentText("The Student ID must be an integer.");
      errorAlert.showAndWait();
      return;
    }

    try {
      int classSectionId = Integer.parseInt(CBClassSectionId.getValue().trim());
      if (classSections.get(classSectionId) == null) {
        errorAlert.setContentText("The Class section ID was not found.");
        errorAlert.showAndWait();
        return;
      }

      try {
        // registerStudent adds the student in the class section
        registrationService.registerStudent(
            students.get(studentId),
            classSections.get(classSectionId));

      } catch (Exception e) {
        // LStudentMessage.setText(e.getMessage());
        errorAlert.setContentText(e.getMessage());
        errorAlert.showAndWait();
        return;
      }

    } catch (Exception e) {
      errorAlert.setContentText("The class section ID must be an integer.");
      errorAlert.showAndWait();
      return;
    }

    infoAlert.setContentText("Student was registered successfully.");
    infoAlert.showAndWait();
  }

  private ObservableList<String> getClassSectionIds(
      Map<Integer, ClassSession> theClassSections) {

    return FXCollections.observableArrayList(
        theClassSections.values().stream().map(ClassSession::getStringId).collect(Collectors.toList()));
  }

  private ObservableList<String> getElegibleInstructorIds(
      List<Instructor> theElegibleInstructors) {
    return FXCollections.observableArrayList(
        theElegibleInstructors.stream().map(Instructor::getId).collect(Collectors.toList()));

  }
}
