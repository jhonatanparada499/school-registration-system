package com.school.app.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.fxml.FXMLLoader;

public class MainSceneController {
  @FXML
  private Tab tabAdministration;
  @FXML
  private Tab tabDashboard;

  public void initialize() {
    try {
      Parent administrationView = FXMLLoader
          .load(getClass().getResource("/com/school/app/view/administrationScene.fxml"));
      Parent dashboardView = FXMLLoader.load(getClass().getResource("/com/school/app/view/dashboardScene.fxml"));

      tabAdministration.setContent(administrationView);
      tabDashboard.setContent(dashboardView);

    } catch (Exception e) {
      System.out.println("Error from mainScne");
      System.out.println(e);
    }
  }
}
