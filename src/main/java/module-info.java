module com.school.app {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;

  // If fxml files are added, they must be declared here as in:
  // opens com.school.app.model to javafx.fxml;

  opens com.school.app to javafx.graphics;

  exports com.school.app;
}
