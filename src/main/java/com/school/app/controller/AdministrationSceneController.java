package com.school.app.controller;

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

  //
  @FXML
  public void initialize() {
    LStudentMessage.setText("");
    LClassSectionMessage.setText("");
  }

  //
  @FXML
  private void createClassSection(ActionEvent event) {
    Map<String, Course> courses = CourseService.load();

    String courseId = TFCourseId.getText();
    if (courses.get(courseId) == null) {
      LClassSectionMessage.setText("Invalid course ID.");
      return;
    }

    Map<String, Instructor> instructors = InstructorService.load();

    String instructorId = TFInstructorId.getText();
    if (instructors.get(instructorId) == null) {
      LClassSectionMessage.setText("Invalid instructor ID.");
      return;
    }

    Map<String, Classroom> classrooms = ClassroomService.load();

    String classroomId = TFClassroomId.getText();
    if (classrooms.get(classroomId) == null) {
      LClassSectionMessage.setText("Invalid classroom ID.");
      return;
    }

    int capacity = 0;
    try {
      capacity = Integer.parseInt(TFCapacity.getText());
      if (capacity < 0) {
        LClassSectionMessage.setText("Capacity can not be negative.");
        return;
      }
    } catch (Exception e) {
      LClassSectionMessage.setText("Invalid capacity");
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
      LClassSectionMessage.setText(e.getMessage());
      return;
    }

    LClassSectionMessage.setText("Class Section was created successfully.");
    TFCourseId.setText("");
    TFInstructorId.setText("");
    TFClassroomId.setText("");
    TFCapacity.setText("");
  }

  @FXML
  void registerStudent(ActionEvent event) {
    Map<String, Student> students = StudentService.load();

    String studentId = TFStudentId.getText();
    if (students.get(studentId) == null) {
      LStudentMessage.setText("Invalid studetn Id.");
      return;
    }

    Map<Integer, ClassSession> classSections = ClassSessionService.load();
    try {
      int classSectionId = Integer.parseInt(TFClassSectionId.getText());
      if (classSections.get(classSectionId) == null) {
        LStudentMessage.setText("Invalid section Id.");
        return;
      }

      // logical execption to register student
      try {
        RegistrationService.registerStudent(students.get(studentId),
            classSections.get(classSectionId));
      } catch (Exception e) {
        LStudentMessage.setText(e.getMessage());
        return;
      }

    } catch (Exception e) {
      LStudentMessage.setText("The ID must be an integer.");
      return;
    }

    LStudentMessage.setText("Student was registered successfully.");
    TFStudentId.setText("");
    TFClassSectionId.setText("");
  }
  //
}
