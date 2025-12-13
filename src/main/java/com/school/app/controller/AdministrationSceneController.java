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

  private ObservableList<String> courseChoices,
      studentChoices,
      classroomChoices,
      instructorChoices,
      elegibleInstructorChoices,
      classSectionChoices;

  private Map<Integer, ClassSession> classSections;
  private Map<String, Course> courses;
  private Map<String, Classroom> classrooms;
  private Map<String, Student> students;
  private Map<String, Instructor> instructors;

  private RegistrationService registrationService;

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

    // the choices can be customized, in this case the only show the ids of the
    // options
    classSectionChoices = getClassSectionChoices(classSections);
    courseChoices = getCourseChoices(courses);
    classroomChoices = getClassroomChoices(classrooms);
    studentChoices = getStudentChoices(students);
    instructorChoices = getInstructorChoices(instructors);
  }

  @FXML
  public void initialize() {
    SCBCourseId.getItems().addAll(courseChoices);
    SCBInstructorId.getItems().addAll(instructorChoices);
    SCBClassroomId.getItems().addAll(classroomChoices);
    SCBStudentId.getItems().addAll(studentChoices);
    SCBClassSectionId.getItems().addAll(classSectionChoices);

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

      // gets elebible instructor depending on selected course id
      List<Instructor> elegibleInstructors = registrationService.findEligibleInstructors(course);
      elegibleInstructorChoices = getElegibleInstructorChoices(
          elegibleInstructors);

      // update Instructor Combobox wiht new instructor ids
      SCBInstructorId.getItems().setAll(elegibleInstructorChoices);

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
    if (courseId == null) {
      return;
    }

    String instructorId = SCBInstructorId.getValue();
    if (instructorId == null) {
      return;
    }

    String classroomId = SCBClassroomId.getValue();
    if (classroomId == null) {
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

      // add class section to instructor teaching assignment
      instructors.get(instructorId).addTeachingAssignment(newClassSection);

      // put new class section in global class sections map
      classSections.put(
          newClassSection.getId(), newClassSection);

      // update items in class sections combobox
      SCBClassSectionId.getItems().setAll(
          getClassSectionChoices(classSections));

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

  // fast permormance under 100 items
  private ObservableList<String> getClassSectionChoices(
      Map<Integer, ClassSession> theClassSections) {

    ObservableList<String> ids = FXCollections.observableArrayList();
    for (ClassSession classSection : theClassSections.values()) {
      ids.add(String.valueOf(classSection.getId()));
    }
    return ids;
  }

  private ObservableList<String> getElegibleInstructorChoices(
      List<Instructor> theElegibleInstructors) {

    ObservableList<String> ids = FXCollections.observableArrayList();
    for (Instructor instructor : theElegibleInstructors) {
      ids.add(instructor.getId());
    }
    return ids;
  }

  private ObservableList<String> getCourseChoices(
      Map<String, Course> theCourses) {

    ObservableList<String> ids = FXCollections.observableArrayList();
    for (Course course : theCourses.values()) {
      ids.add(course.getCourseId());
    }
    return ids;
  }

  private ObservableList<String> getClassroomChoices(
      Map<String, Classroom> theClassrooms) {

    ObservableList<String> ids = FXCollections.observableArrayList();
    for (Classroom classroom : theClassrooms.values()) {
      ids.add(classroom.getRoomNumber());
    }
    return ids;
  }

  private ObservableList<String> getStudentChoices(
      Map<String, Student> theStudents) {

    ObservableList<String> ids = FXCollections.observableArrayList();
    for (Student student : theStudents.values()) {
      ids.add(student.getId());
    }
    return ids;
  }

  private ObservableList<String> getInstructorChoices(
      Map<String, Instructor> theInstructors) {

    ObservableList<String> ids = FXCollections.observableArrayList();
    for (Instructor instructor : theInstructors.values()) {
      ids.add(instructor.getId());
    }
    return ids;
  }

}
