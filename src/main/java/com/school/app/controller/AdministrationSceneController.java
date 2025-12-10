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
// import javafx.fxml.FXML;
// import javafx.scene.control.ComboBox;

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

  //
  // @FXML
  // private TextField TFClassroomId;
  //
  // @FXML
  // private TextField TFCourseId;
  //
  // @FXML
  // private TextField TFInstructorId;

  @FXML
  private TextField TFStudentId;

  @FXML
  private TextField TFClassSectionId;

  @FXML
  private Button BRegister;

  //
  private Alert infoAlert;

  private Alert errorAlert;

  //
  private ObservableList<String> courseIds;
  private ObservableList<String> classroomIds;
  private ObservableList<String> instructorIds;
  private ObservableList<String> elegibleInstructorIds;

  //
  @FXML
  public void initialize() {
    CBCourseId.setVisibleRowCount(5);
    CBInstructorId.setVisibleRowCount(5);
    CBClassroomId.setVisibleRowCount(5);

    // load courses
    Map<String, Course> courses = CourseService.load();
    // courseids gets the course ids Strings as an List<String>
    courseIds = FXCollections.observableArrayList(
        courses.values().stream().map(Course::getCourseId).collect(Collectors.toList()));

    Map<String, Classroom> classrooms = ClassroomService.load();

    // get classroom ids
    classroomIds = FXCollections.observableArrayList(
        classrooms.values().stream().map(Classroom::getRoomNumber).collect(Collectors.toList()));

    // get instructor ids
    Map<String, Instructor> instructors = InstructorService.load();
    instructorIds = FXCollections.observableArrayList(
        instructors.values().stream().map(Instructor::getId).collect(Collectors.toList()));

    // helper class for comboboxes courses ids and classroomcs
    FilterableComboBox CBCourse = new FilterableComboBox(CBCourseId, courseIds);
    FilterableComboBox CBInstructor = new FilterableComboBox(CBInstructorId, instructorIds);
    new FilterableComboBox(CBClassroomId, classroomIds);

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

        List<Instructor> elegibleInstructors = RegistrationService.findEligibleInstructors(
            course);

        // precondition for elegibleInstructors
        if (elegibleInstructors == null || elegibleInstructors.isEmpty()) {
          return;
        }

        elegibleInstructorIds = FXCollections.observableArrayList(
            elegibleInstructors.stream().map(Instructor::getId).collect(Collectors.toList()));

        CBInstructorId.getEditor().clear();

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

  //
  @FXML
  private void createClassSection(ActionEvent event) {
    Map<String, Course> courses = CourseService.load();

    // in case user enters lowercase
    String courseId = CBCourseId.getValue().trim().toUpperCase();
    if (courses.get(courseId) == null) {
      // LClassSectionMessage.setText("Invalid course ID.");
      errorAlert.setContentText("Invalid course ID.");
      errorAlert.showAndWait();
      return;
    }

    Map<String, Instructor> instructors = InstructorService.load();

    String instructorId = CBInstructorId.getValue().trim().toUpperCase();

    if (instructors.get(instructorId) == null) {
      // LClassSectionMessage.setText("Invalid instructor ID.");
      errorAlert.setContentText("Invalid instructor ID.");
      errorAlert.showAndWait();
      return;
    }

    Map<String, Classroom> classrooms = ClassroomService.load();

    String classroomId = CBClassroomId.getValue().trim().toUpperCase();

    if (classrooms.get(classroomId) == null) {
      // LClassSectionMessage.setText("Invalid classroom ID.");
      errorAlert.setContentText("Invalid classroom ID.");
      errorAlert.showAndWait();
      return;
    }

    int capacity = 0;
    try {
      capacity = Integer.parseInt(TFCapacity.getText().trim());
      if (capacity < 0) {
        // LClassSectionMessage.setText("Capacity can not be negative.");
        errorAlert.setContentText("Capacity cannot be negative.");
        errorAlert.showAndWait();
        return;
      }
    } catch (Exception e) {
      // LClassSectionMessage.setText("Invalid capacity");
      errorAlert.setContentText("Invalid capacity");
      errorAlert.showAndWait();
      return;
    }

    RegistrationService newRegistration = new RegistrationService();
    try {
      ClassSession newClassSection = newRegistration.createClassSection(courseId,
          instructorId,
          classroomId,
          capacity);
      // write new class sectio to csv file
      RegistrationService.saveClassSection(newClassSection);

    } catch (Exception e) {
      // LClassSectionMessage.setText(e.getMessage());
      errorAlert.setContentText(e.getMessage());
      errorAlert.showAndWait();
      return;
    }

    // LClassSectionMessage.setText("Class Section was created successfully.");
    infoAlert.setContentText("Class Section was created successfully.");
    infoAlert.showAndWait();
  }

  @FXML
  void registerStudent(ActionEvent event) {
    Map<String, Student> students = StudentService.load();

    String studentId = TFStudentId.getText().trim();

    if (students.get(studentId) == null) {
      // LStudentMessage.setText("Invalid studetn Id.");
      errorAlert.setContentText("The Student ID must be an integer.");
      errorAlert.showAndWait();
      return;
    }

    Map<Integer, ClassSession> classSections = ClassSessionService.load();
    try {
      int classSectionId = Integer.parseInt(TFClassSectionId.getText().trim());
      if (classSections.get(classSectionId) == null) {
        // LStudentMessage.setText("Invalid section Id.");
        errorAlert.setContentText("The Class section ID was not found.");
        errorAlert.showAndWait();
        return;
      }

      // logical execption to register student
      RegistrationService newRegistration = new RegistrationService();
      try {
        newRegistration.registerStudent(students.get(studentId),
            classSections.get(classSectionId));
      } catch (Exception e) {
        // LStudentMessage.setText(e.getMessage());
        errorAlert.setContentText(e.getMessage());
        errorAlert.showAndWait();
        return;
      }

    } catch (Exception e) {
      // LStudentMessage.setText("The ID must be an integer.");
      errorAlert.setContentText("The class section ID must be an integer.");
      errorAlert.showAndWait();
      return;
    }

    // LStudentMessage.setText("Student was registered successfully.");
    // TFStudentId.setText("");
    // TFClassSectionId.setText("");
    infoAlert.setContentText("Student was registered successfully.");
    infoAlert.showAndWait();
  }
  //

  //
}
