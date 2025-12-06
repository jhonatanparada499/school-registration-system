package com.school.app;

// import javafx.application.Application;
// import javafx.scene.Scene;
// import javafx.scene.control.Label;
// import javafx.scene.layout.StackPane;
// import javafx.stage.Stage;
//
// public class Main extends Application {
//
// @Override
// public void start(Stage primaryStage) {
// primaryStage.setTitle("Hello World!");
//
// Label helloLabel = new Label("Hello, JavaFX!");
//
// // Create a StackPane as the root layout for the scene
// // StackPane centers its children by default
// StackPane root = new StackPane();
// root.getChildren().add(helloLabel); // Add the label to the StackPane
//
// // Create a Scene with the root layout and specified dimensions
// Scene scene = new Scene(root, 300, 200); // Width 300, Height 200
//
// primaryStage.setScene(scene);
//
// primaryStage.show();
// }
//
// public static void main(String[] args) {
// launch(args);
// }
// }
import com.school.app.view.cli.MainMenu;

public class Main {
  public static void main(String[] args) {
    MainMenu.display();
  }
}
