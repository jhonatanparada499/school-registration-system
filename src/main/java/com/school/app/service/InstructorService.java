package com.school.app.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.io.BufferedReader;

import com.school.app.model.Instructor;
import com.school.app.model.Course;
// import com.school.app.service.ClassSessionService;

// Inspiration: 
// https://medium.com/@AlexanderObregon/javas-scanner-usedelimiter-method-explained-8e09e2baf831

public class InstructorService {
  private static final Path path = Paths.get("data", "Instructor.csv");
  private static final String filePath = path.toAbsolutePath().toString();

  /**
   * This static method parses Instructor.cvs and returns a
   * HashMap<String, Instructor> object.
   */
  public static Map<String, Instructor> load(
      Map<String, Course> theCourses) {

    Map<String, Instructor> instructors = new HashMap<>();

    // try-resource closes file automatically
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {

        if (line.trim().isEmpty()) {
          continue;
        }

        String[] columns = line.split(",");

        // If records columsn are missing and Exception will be thrown
        String idField = columns[0].trim();
        String nameField = columns[1].trim();
        String qualifiedCoursesIdField = columns[2].trim();

        // split field by the pipe character
        String[] qualifiedCoursesIdArray = qualifiedCoursesIdField.split("\\|");

        List<String> qualifiedCoursesId = new ArrayList<>();
        qualifiedCoursesId.addAll(Arrays.asList(qualifiedCoursesIdArray));

        List<Course> qualifiedCourses = new ArrayList<>();
        for (String qualifiedCourseId : qualifiedCoursesId) {
          qualifiedCourses.add(theCourses.get(qualifiedCourseId));
        }

        Instructor instructor = new Instructor(
            idField,
            nameField,
            qualifiedCourses);

        instructors.put(idField, instructor);
      }

    } catch (Exception e) {
      System.out.println(e);
    }

    return instructors;
  }

}
