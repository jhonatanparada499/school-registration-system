package com.school.app.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.school.app.model.ClassSession;

import java.util.List;

public class ClassSessionService {
  public static Map<Integer, ClassSession> load() {
    Map<Integer, ClassSession> classSections = new HashMap<>();

    // Relative path to ClassSession.csv (expects data/ClassSession.csv at project
    // root)
    Path path = Paths.get("data", "ClassSession.csv");
    String filePath = String.valueOf(path);

    // try-resource closes file automatically
    try (Scanner scanner = new Scanner(new File(filePath))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();

        if (line.trim().isEmpty()) {
          continue;
        }

        // if a field is empty it must have a blank space
        // it has to do with how split works
        // make sure csv is , , and not ,,
        String[] columns = line.split(",");

        int idField = Integer.parseInt(columns[0]);
        String courseIdField = columns[1].trim();
        String instructorIdField = columns[2].trim();
        String classroomIdField = columns[3].trim();
        int sectionNumberField = Integer.parseInt(columns[4].trim());
        int maxCapacityField = Integer.parseInt(columns[5].trim());
        String enrolledStudentIdsField = columns[6].trim();

        String[] array = enrolledStudentIdsField.split("\\|");
        List<String> enrolledStudentsIds = new ArrayList<>();

        if (!enrolledStudentIdsField.trim().isEmpty()) {
          // cannot be = to Arrayaslist for add method later
          enrolledStudentsIds.addAll(Arrays.asList(array));
        }

        ClassSession classSection = new ClassSession(
            idField,
            courseIdField,
            instructorIdField,
            classroomIdField,
            sectionNumberField,
            maxCapacityField,
            enrolledStudentsIds);

        classSections.put(
            idField,
            classSection);
      }

    } catch (Exception e) {
      System.out.println("From ClassSessionService");
      System.out.println(e);
    }

    return classSections;
  }
}
