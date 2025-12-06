package com.school.app.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.school.app.model.Student;

public class StudentService {
  public static Map<String, Student> load() {
    Map<String, Student> students = new HashMap<>();

    Path path = Paths.get("data", "Student.csv");
    String filePath = String.valueOf(path);

    try (Scanner scanner = new Scanner(new File(filePath))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();

        if (line.trim().isEmpty()) {
          continue;
        }

        String[] columns = line.split(",");

        String idField = columns[0].trim();
        String nameField = columns[1].trim();
        String majorField = columns[2].trim();

        // Student.csv should have a fourth field similar to
        // Instructor.csv that lists the classes they are enroolled in
        Student student = new Student(
            idField,
            nameField,
            majorField);

        students.put(idField, student);
      }
    } catch (

    Exception e) {
      System.out.println("Exception from studentService");
      System.out.println(e);
    }

    return students;
  }
}
