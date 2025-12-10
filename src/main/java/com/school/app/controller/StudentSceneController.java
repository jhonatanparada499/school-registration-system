package com.school.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Map;

import com.school.app.model.Student;
import com.school.app.service.StudentService;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;

import java.util.ArrayList;

// inspiration: https://code.makery.ch/blog/javafx-2-tableview-filter/

public class StudentSceneController {
  private Map<String, Student> students;

  // @FXML
  // private Button BFilter;

  @FXML
  private TextField TFFilterSearchBar;

  @FXML
  private TableColumn<Student, String> studentIdCol;

  @FXML
  private TableColumn<Student, String> studentMajorCol;

  @FXML
  private TableColumn<Student, String> studentNameCol;

  @FXML
  private TableView<Student> studentsTable;

  //
  private ObservableList<Student> masterData = FXCollections.observableArrayList();
  private ObservableList<Student> filteredData = FXCollections.observableArrayList();

  public StudentSceneController() {
    students = StudentService.load();

    // Add some sample data to the master data
    masterData.addAll(students.values());

    // Initially add all data to filtered data
    filteredData.addAll(masterData);

    masterData.addListener(new ListChangeListener<Student>() {
      @Override
      public void onChanged(ListChangeListener.Change<? extends Student> change) {
        updateFilteredData();
      }
    });
  }

  @FXML
  public void initialize() {

    studentIdCol.setCellValueFactory(
        new PropertyValueFactory<Student, String>("id"));

    studentNameCol.setCellValueFactory(
        new PropertyValueFactory<Student, String>("name"));

    studentMajorCol.setCellValueFactory(
        new PropertyValueFactory<Student, String>("major"));

    // studentsTable.get
    // Add filtered data to the table
    studentsTable.setItems(filteredData);

    // Listen for text changes in the filter text field
    TFFilterSearchBar.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable,
          String oldValue, String newValue) {

        updateFilteredData();
      }
    });
  }

  /**
   * Updates the filteredData to contain all data from the masterData that
   * matches the current filter.
   */
  private void updateFilteredData() {
    filteredData.clear();

    for (Student p : masterData) {
      if (matchesFilter(p)) {
        filteredData.add(p);
      }
    }

    // Must re-sort table after items changed
    reapplyTableSortOrder();
  }

  /**
   * Returns true if the student matches the current filter. Lower/Upper case
   * is ignored.
   * 
   * @param student
   * @return
   */
  private boolean matchesFilter(Student student) {
    String filterString = TFFilterSearchBar.getText();
    if (filterString == null || filterString.isEmpty()) {
      // No filter --> Add all.
      return true;
    }

    String lowerCaseFilterString = filterString.toLowerCase();

    if (student.getId().toLowerCase().contains(lowerCaseFilterString)) {
      return true;
    } else if (student.getName().toLowerCase().contains(lowerCaseFilterString)) {
      return true;
    } else if (student.getMajor().toLowerCase().contains(lowerCaseFilterString)) {
      return true;
    }

    return false; // Does not match
  }

  private void reapplyTableSortOrder() {
    ArrayList<TableColumn<Student, ?>> sortOrder = new ArrayList<>(studentsTable.getSortOrder());
    studentsTable.getSortOrder().clear();
    studentsTable.getSortOrder().addAll(sortOrder);
  }
}
