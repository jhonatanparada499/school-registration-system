package com.school.app.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.List;

import com.school.app.model.Instructor;

// Inspiration: 
// https://medium.com/@AlexanderObregon/javas-scanner-usedelimiter-method-explained-8e09e2baf831

public class InstructorService {
  /**
   * This static method parses Instructor.cvs and returns a
   * HashMap<String, Instructor> object.
   */
  public static Map<String, Instructor> load() {
    Map<String, Instructor> instructors = new HashMap<>();

    // Relative path to Instructor.csv (expects data/Instructor.csv at project root)
      Path filePath = Paths.get("data", "Instructor.csv");

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
