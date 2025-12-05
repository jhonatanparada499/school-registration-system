package com.school.app.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.school.app.model.Course;

public class CourseService {
  public static Map<String, Course> load() {
    Map<String, Course> courses = new HashMap<>();

    // Relative path to ClassSession.csv (expects data/ClassSession.csv at project
    // root)
    Path path = Paths.get("data", "ClassSession.csv");
    String filePath = String.valueOf(path);

    // try-resource closes file automatically
    try (Scanner scanner = new Scanner(new File(filePath))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();

      while (scanner.hasNext()) {
        String idField = scanner.next();
        String nameField = scanner.next();
        int creditsField = scanner.nextInt();

        Course course = new Course(idField,
            nameField, creditsField);
        courses.put(idField, course);
      }

    } catch (Exception e) {
      System.out.println(e);
    }

    return courses;
  }
}
