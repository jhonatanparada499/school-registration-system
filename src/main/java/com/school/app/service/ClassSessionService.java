package com.school.app.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import com.school.app.model.ClassSession;
import com.school.app.model.Course;
import com.school.app.model.Instructor;
import com.school.app.model.Classroom;

import java.util.List;

public class ClassSessionService {
  public static List<ClassSession> load() {
    List<ClassSession> classSections = new ArrayList<>();

   // Relative path to ClassSession.csv (expects data/ClassSession.csv at project root)
    Path filePath = Paths.get("data", "ClassSession.csv");

    // try-resource closes file automatically
    try (Scanner scanner = new Scanner(new File(filePath))) {
      while (scanner.hasNext()) {
        String line = scanner.next();

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

        classSections.add(classSection);
      }

    } catch (Exception e) {
      System.out.println("From ClassSessionService");
      e.printStackTrace();
    }

    return classSections;
  }
}
