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

import com.school.app.model.ClassSession;
import com.school.app.model.Instructor;
// import com.school.app.service.ClassSessionService;

// Inspiration: 
// https://medium.com/@AlexanderObregon/javas-scanner-usedelimiter-method-explained-8e09e2baf831

public class InstructorService {
  /**
   * This static method parses Instructor.cvs and returns a
   * HashMap<String, Instructor> object.
   */
  public static Map<String, Instructor> load(
      Map<Integer, ClassSession> classSections) {
    Map<String, Instructor> instructors = new HashMap<>();

    // Relative path to Instructor.csv (expects data/Instructor.csv at project root)
    Path path = Paths.get("data", "Instructor.csv");
    String filePath = String.valueOf(path);

    // try-resource closes file automatically
    try (Scanner scanner = new Scanner(new File(filePath))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();

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

        // only ocassion that a loader uses the loaded data of a loader
        List<Integer> teachingAssignmetIds = new ArrayList<>();
        for (ClassSession classSection : classSections.values()) {
          if (classSection.getInstructor().equals(idField)) {
            teachingAssignmetIds.add(classSection.getId());
          }
        }

        Instructor instructor = new Instructor(
            idField,
            nameField,
            qualifiedCoursesId,
            teachingAssignmetIds);

        instructors.put(idField, instructor);
      }

    } catch (Exception e) {
      System.out.println(e);
    }

    return instructors;
  }

}
