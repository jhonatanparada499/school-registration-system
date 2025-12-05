package com.school.app.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.school.app.model.Classroom;

public class ClassroomService {
  public static Map<String, Classroom> load() {
    Map<String, Classroom> classrooms = new HashMap<>();

    // Relative path to Classroom.csv (expects data/Classroom.csv at project root)
    Path path = Paths.get("data", "Classroom.csv");
    String filePath = String.valueOf(path);

    // try-resource closes file automatically
    try (Scanner scanner = new Scanner(new File(filePath))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();

      while (scanner.hasNext()) {
        String roomNumberField = scanner.next();
        Boolean hasComputerField = scanner.nextBoolean();
        Boolean hasSmartboardField = scanner.nextBoolean();

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
