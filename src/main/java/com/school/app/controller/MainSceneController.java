package com.school.app.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.fxml.FXMLLoader;

import com.school.app.service.StudentService;
import com.school.app.service.ClassSessionService;
import com.school.app.service.ClassroomService;
import com.school.app.service.CourseService;
import com.school.app.service.InstructorService;
import com.school.app.model.*;

import java.util.Map;

import com.school.app.service.RegistrationService;

public class MainSceneController {
  @FXML
  private Tab tabAdministration;
  @FXML
  private Tab tabDashboard;
  @FXML
  private Tab tabStudent;

  FXMLLoader loader;
  Parent view;

  Map<String, Student> students;
  Map<String, Classroom> classrooms;
  Map<String, Instructor> instructors;
  Map<String, Course> courses;
  Map<Integer, ClassSession> classSections;

  String viewPath;
  String adminstrationScene;
  String dashboardScene;
  String studentScene;

  private RegistrationService registrationService;

  public MainSceneController() {

    students = StudentService.load();
    classrooms = ClassroomService.load();
    courses = CourseService.load();
    instructors = InstructorService.load(courses);

    classSections = ClassSessionService.load(
        courses,
        students,
        instructors,
        classrooms);

    viewPath = "/com/school/app/view/";
    adminstrationScene = viewPath + "administrationScene.fxml";
    dashboardScene = viewPath + "dashboardScene.fxml";
    studentScene = viewPath + "studentScene.fxml";

    registrationService = new RegistrationService(
        classSections,
        courses,
        classrooms,
        // students,
        instructors);
  }

  @FXML
  public void initialize() {
    try {
      // Set up Administration Tab
      loader = new FXMLLoader(getClass().getResource(adminstrationScene));
      loader.setController(
          new AdministrationSceneController(
              classSections,
              courses,
              classrooms,
              students,
              instructors,
              registrationService));
      view = loader.load();
      tabAdministration.setContent(view);

      // Set up Dashboard Tab
      loader = new FXMLLoader(getClass().getResource(dashboardScene));
      loader.setController(
          new DashboardSceneController(
              classSections,
              registrationService));
      view = loader.load();
      tabDashboard.setContent(view);

      // Set up Student Tab
      loader = new FXMLLoader(getClass().getResource(studentScene));
      loader.setController(
          // pass students map object to controller constructor
          new StudentSceneController(students));
      view = loader.load();
      tabStudent.setContent(view);

    } catch (Exception e) {
      System.out.println("Error from mainScene controller.");
      System.out.println(e);
    }
  }
}
