package com.school.app.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.List;

import com.school.app.model.Instructor;

// inspiration: 
// https://medium.com/@AlexanderObregon/javas-scanner-usedelimiter-method-explained-8e09e2baf831

public class InstructorService {
  /**
   * This static method parses Instructor.cvs and returns a
   * HashMap<String, Instructor> instance.
   */
  public static HashMap<String, Instructor> load() {
    HashMap<String, Instructor> instructors = new HashMap<>();

    String homeDir = "/home/jhonatan/";
    String fileDir = homeDir + "Projects/Github/jhonatanparada499/" +
        "school-registration-system/data/";
    String fileName = "Instructor.csv";
    String filePath = fileDir + fileName;

    // try-resource closes file automatically
    try (Scanner scanner = new Scanner(new File(filePath))) {
      scanner.useDelimiter(",|\\n"); // match: comma(,) or new line(\n)

      while (scanner.hasNext()) {
        String idField = scanner.next();
        String nameField = scanner.next();
        String qualifiedCoursesField = scanner.next();

        // split field by the pipe character
        String[] qualifiedCoursesArray = qualifiedCoursesField.split("\\|");
        List<String> qualifiedCourses = new ArrayList<>(
            Arrays.asList(qualifiedCoursesArray));

        Instructor instructor = new Instructor(
            idField, nameField, qualifiedCourses);
        instructors.put(idField, instructor);
      }

    } catch (Exception e) {
      System.out.println(e);
    }

    return instructors;
  }

}
