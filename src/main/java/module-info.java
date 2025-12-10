module com.school.app {
  requires transitive javafx.controls;
  requires transitive javafx.fxml;
  requires transitive javafx.graphics;

  // If fxml files are added, they must be declared here as in:
  opens com.school.app.controller to javafx.fxml;

  opens com.school.app to javafx.graphics;
  opens com.school.app.model to javafx.base;

  exports com.school.app;
}
