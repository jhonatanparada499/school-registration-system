package com.school.app.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.school.app.model.ClassSession;
import com.school.app.model.Course;
import com.school.app.model.Instructor;
import com.school.app.model.Classroom;

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

        String[] columns = line.split(",");

        String courseField = columns[0];
        String instructorIdField = columns[1];
        String classroomField = columns[2];

        int sectionNumberField = Integer.parseInt(columns[3]);
        int maxCapacityField = Integer.parseInt(columns[4]);
        int enrolledStudentsField = Integer.parseInt(columns[5]);

        Map<String, Course> courses = CourseService.load();
        Course course = courses.get(courseField);

        Map<String, Instructor> instructors = InstructorService.load();
        Instructor instructor = instructors.get(instructorIdField);

        Map<String, Classroom> classrooms = ClassroomService.load();
        Classroom classroom = classrooms.get(classroomField);

        ClassSession classSection = new ClassSession(
            course,
            instructor,
            classroom,
            sectionNumberField,
            maxCapacityField,
            enrolledStudentsField);

        classSections.put(
            idField,
            classSection);
      }

    } catch (Exception e) {
      System.out.println("From ClassSessionService");
      e.printStackTrace();
    }

    return classSections;
  }
}
