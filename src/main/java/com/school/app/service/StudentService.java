package com.school.app.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.school.app.model.Student;

public class StudentService {
  private static final Path path = Paths.get("data", "Student.csv");
  private static final String filePath = path.toAbsolutePath().toString();

  public static Map<String, Student> load() {
    Map<String, Student> students = new HashMap<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;

      while ((line = reader.readLine()) != null) {

        if (line.trim().isEmpty()) {
          continue;
        }

        String[] columns = line.split(",");

        String idField = columns[0].trim();
        String nameField = columns[1].trim();
        String majorField = columns[2].trim();

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
