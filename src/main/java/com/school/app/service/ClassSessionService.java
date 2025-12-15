package com.school.app.service;

import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Path;

import com.school.app.model.*;

import java.util.List;

public class ClassSessionService {
  private static final Path path = Paths.get("data", "ClassSession.csv");
  private static final String filePath = path.toAbsolutePath().toString();

  public static Map<Integer, ClassSession> load(
      Map<String, Course> theCourses,
      Map<String, Student> theStudents,
      Map<String, Instructor> theInstructors,
      Map<String, Classroom> theClassrooms) {

    Map<Integer, ClassSession> classSections = new HashMap<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {

        if (line.trim().isEmpty()) {
          continue;
        }

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

        Course course = theCourses.get(courseIdField);
        Instructor instructor = theInstructors.get(instructorIdField);
        Classroom classroom = theClassrooms.get(classroomIdField);

        ClassSession classSection = new ClassSession(
            idField,
            course,
            instructor,
            classroom,
            sectionNumberField,
            maxCapacityField);

        // add student object to enrolled student in class section
        for (String studentId : enrolledStudentsIds) {
          Student student = theStudents.get(studentId);
          student.addEnrolledClass(classSection);
          classSection.addEnrolledStudent(student);
        }

        // add the class section to the taeching assignment
        instructor.addTeachingAssignment(classSection);

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
