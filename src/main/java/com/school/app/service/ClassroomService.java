package com.school.app.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.school.app.model.Classroom;

public class ClassroomService {
  public static Map<String, Classroom> load() {
    Map<String, Classroom> classrooms = new HashMap<>();

    String homeDir = "/home/jhonatan/"; //code added to lead 
String filePath = homeDir +
    "Projects/Github/jhonatanparada499/" +
    "school-registration-system/data/classroom/Classroom.csv";

    // try-resource closes file automatically
    try (Scanner scanner = new Scanner(new File(filePath))) {
      scanner.useDelimiter(",|\\n"); // match: comma(,) or new line(\n)

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
