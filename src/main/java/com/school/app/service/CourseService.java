package com.school.app.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.school.app.model.Course;

public class CourseService {
  private static final Path path = Paths.get("data", "Course.csv");
  private static final String filePath = path.toAbsolutePath().toString();

  public static Map<String, Course> load() {
    Map<String, Course> courses = new HashMap<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {

        if (line.trim().isEmpty()) {
          continue;
        }

        String[] columns = line.split(",");

        String idField = columns[0];
        String nameField = columns[1];
        int creditsField = Integer.parseInt(columns[2]);

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
