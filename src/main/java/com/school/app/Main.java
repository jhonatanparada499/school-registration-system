package com.school.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.net.URL;

/**
 *
 * Inspiration: https://www.vojtechruzicka.com/javafx-fxml-scene-builder/
 */

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) {

    FXMLLoader loader = new FXMLLoader();
    URL xmlUrl = getClass().getResource("/com/school/app/view/mainScene.fxml");
    loader.setLocation(xmlUrl);

    try {
      Parent root = loader.load();
      primaryStage.setTitle("School Registration System");
      primaryStage.setScene(new Scene(root));
      primaryStage.show();
    } catch (Exception e) {
      System.out.println("Error from loader");
      System.out.println(e);
    }
  }

  public static void main(String[] args) {
    launch();
  }
}
