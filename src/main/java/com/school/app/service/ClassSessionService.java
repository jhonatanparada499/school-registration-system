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

    String homeDir = "/home/jhonatan/";
    String fileDir = homeDir +
        "Projects/Github/jhonatanparada499/" +
        "school-registration-system/data/";
    String fileName = "ClassSession.csv";
    String filePath = fileDir + fileName;

    // try-resource closes file automatically
    try (Scanner scanner = new Scanner(new File(filePath))) {
      scanner.useDelimiter(",|\\n"); // match: comma(,) or new line(\n)

      while (scanner.hasNext()) {
        String courseField = scanner.next();
        String instructorField = scanner.next();
        String classroomField = scanner.next();
        int sectionNumberField = scanner.nextInt();
        int maxCapacityField = scanner.nextInt();
        int enrolledStudentsField = scanner.nextInt();

        Map<String, Course> courses = CourseService.load();
        Course course = courses.get(courseField);

        Map<String, Instructor> instructors = InstructorService.load();
        Instructor instructor = instructors.get(instructorField);

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
            courseField + "-" + sectionNumberField,
            classSection);
      }

    } catch (Exception e) {
      System.out.println(e);
    }

    return classSections;
  }
}
