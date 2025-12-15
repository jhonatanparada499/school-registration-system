package com.school.app.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.school.app.model.Classroom;

public class ClassroomService {
  private static final Path path = Paths.get("data", "Classroom.csv");
  private static final String filePath = path.toAbsolutePath().toString();

  public static Map<String, Classroom> load() {
    Map<String, Classroom> classrooms = new HashMap<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {

        // ignores empty lines in case csv has extra spaces
        if (line.trim().isEmpty()) {
          continue;
        }

        String[] columns = line.split(",");

        String roomNumberField = columns[0];
        boolean hasComputerField = Boolean.parseBoolean(columns[1]);
        boolean hasSmartboardField = Boolean.parseBoolean(columns[2]);

        Classroom classroom = new Classroom(
            roomNumberField,
            hasComputerField,
            hasSmartboardField);
        classrooms.put(roomNumberField, classroom);
      }

    } catch (Exception e) {
      System.out.println(e);
    }

    return classrooms;
  }
}
