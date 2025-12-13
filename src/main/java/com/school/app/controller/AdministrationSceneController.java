package com.school.app.controller;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.Map;
import com.school.app.model.*;
import com.school.app.service.RegistrationService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.Collectors;

import javafx.scene.control.Alert;

import org.controlsfx.control.SearchableComboBox;

/**
 * Methods:
 * 1. createClassSection
 * 2. registerStudent
 */

public class AdministrationSceneController {
  @FXML
  private Button BCreate;
  @FXML
  private SearchableComboBox<String> SCBCourseId;
  @FXML
  private SearchableComboBox<String> SCBInstructorId;
  @FXML
  private SearchableComboBox<String> SCBClassroomId;
  @FXML
  private TextField TFCapacity;
  @FXML
  private SearchableComboBox<String> SCBStudentId;
  @FXML
  private SearchableComboBox<String> SCBClassSectionId;
  @FXML
  private Button BRegister;

  private Alert infoAlert,
      errorAlert;

  private ObservableList<String> courseIds,
      studentIds,
      classroomIds,
      instructorIds,
      elegibleInstructorIds,
      classSectionIds;

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
    SCBCourseId.getItems().addAll(courseIds);
    SCBInstructorId.getItems().addAll(instructorIds);
    SCBClassroomId.getItems().addAll(classroomIds);
    SCBStudentId.getItems().addAll(studentIds);
    SCBClassSectionId.getItems().addAll(classSectionIds);

    // attach listener to when the combobox dropdown is displayed and hidden
    SCBCourseId.showingProperty().addListener((obs, wasShowing, isShowing) -> {

      String courseId = SCBCourseId.getValue();
      if (courseId == null) {
        return;
      }

      Course course = courses.get(courseId);
      if (course == null) {
        return;
      }

      List<Instructor> elegibleInstructors = registrationService.findEligibleInstructors(course);
      elegibleInstructorIds = getElegibleInstructorIds(
          elegibleInstructors);

      // update Instructor Combobox wiht new instructor ids
      SCBInstructorId.getItems().setAll(elegibleInstructorIds);

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
    String courseId = SCBCourseId.getValue();
    String instructorId = SCBInstructorId.getValue();
    String classroomId = SCBClassroomId.getValue();

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

      // add class section to instructor teaching assignment
      instructors.get(instructorId).addTeachingAssignment(newClassSection);

      // put new class section in global class sections map
      classSections.put(
          newClassSection.getId(), newClassSection);

      // update items in class sections combobox
      SCBClassSectionId.getItems().setAll(
          getClassSectionIds(classSections));

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
    String studentId = SCBStudentId.getValue();
    int classSectionId = Integer.parseInt(SCBClassSectionId.getValue());

    try {
      // registerStudent adds the student in the class section
      registrationService.registerStudent(
          students.get(studentId),
          classSections.get(classSectionId));

    } catch (Exception e) {
      errorAlert.setContentText(e.getMessage());
      errorAlert.showAndWait();
      return;
    }

    infoAlert.setContentText("Student was registered successfully.");
    infoAlert.showAndWait();
  }

  private ObservableList<String> getClassSectionIds(
      Map<Integer, ClassSession> theClassSections) {

    return theClassSections.values().stream().map(ClassSession::getStringId)
        .collect(Collectors.toCollection(FXCollections::observableArrayList));
  }

  private ObservableList<String> getElegibleInstructorIds(
      List<Instructor> theElegibleInstructors) {

    return theElegibleInstructors.stream().map(Instructor::getId)
        .collect(Collectors.toCollection(FXCollections::observableArrayList));
  }
}
