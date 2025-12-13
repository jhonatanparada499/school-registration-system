package com.school.app.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.school.app.model.ClassSession;
import com.school.app.service.RegistrationService;

import java.util.Map;

public class DashboardSceneController {
  @FXML
  private Label LLastUpdated;
  @FXML
  private Button BRefresh;
  @FXML
  private Button BSave;
  @FXML
  private TableView<ClassSession> classSectionsTable;
  @FXML
  private TableColumn<ClassSession, Integer> idCol;
  @FXML
  private TableColumn<ClassSession, String> courseIdCol;
  @FXML
  private TableColumn<ClassSession, String> enrolledCapacityCol;
  @FXML
  private TableColumn<ClassSession, String> instructorCol;
  @FXML
  private TableColumn<ClassSession, String> roomIdCol;
  @FXML
  private TableColumn<ClassSession, String> sectionNumberCol;

  private Map<Integer, ClassSession> classSections;

  private RegistrationService registrationService;

  public DashboardSceneController(
      Map<Integer, ClassSession> theClassSections,
      RegistrationService theRegistrationService) {
    classSections = theClassSections;
    registrationService = theRegistrationService;
  }

  @FXML
  public void initialize() {
    idCol.setCellValueFactory(
        new PropertyValueFactory<>("id"));

    courseIdCol.setCellValueFactory(
        new PropertyValueFactory<>("courseId"));

    sectionNumberCol.setCellValueFactory(
        new PropertyValueFactory<>("formatSectionNumber"));

    instructorCol.setCellValueFactory(
        new PropertyValueFactory<>("instructorName"));

    roomIdCol.setCellValueFactory(
        new PropertyValueFactory<>("classroomNumber"));

    enrolledCapacityCol.setCellValueFactory(
        new PropertyValueFactory<>("enrolledCapacity"));

    classSectionsTable.getItems().addAll(classSections.values());

    LLastUpdated.setText(
        LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm:ss a")));
  }

  @FXML
  void refreshTable(ActionEvent event) {
    // since all interfaces share the same objects, If I update
    // classSections in one file, it will be reflected in this
    // file as well
    classSectionsTable.getItems().setAll(classSections.values());

    LLastUpdated.setText(
        LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm:ss a")));
  }

  @FXML
  void saveClassSections(ActionEvent event) {
    registrationService.writeToClassSections();

    LLastUpdated.setText(
        LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm:ss a")));

    // add logic to only save if class sections changed
    // since last save action, need to initialize as well
    // lastSavedClassSections = new Class
  }
}
