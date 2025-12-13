package com.school.app.service;

import java.nio.file.Paths;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.school.app.model.ClassSession;
import com.school.app.model.Student;
import com.school.app.model.Instructor;
import com.school.app.model.Course;
import com.school.app.model.Classroom;

import java.util.List;

public class ClassSessionService {
  public static final String filePath = String.valueOf(
      Paths.get("data", "ClassSession.csv"));

  public static Map<Integer, ClassSession> load(
      Map<String, Course> theCourses,
      Map<String, Student> theStudents,
      Map<String, Instructor> theInstructors,
      Map<String, Classroom> theClassrooms) {

    Map<Integer, ClassSession> classSections = new HashMap<>();

    // try-resource closes file automatically
    try (Scanner scanner = new Scanner(new File(filePath))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();

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

  public static List<String> getIds(
      Map<Integer, ClassSession> theClassSections) {

    List<String> ids = new ArrayList<>();

    for (ClassSession classSection : theClassSections.values()) {
      ids.add(String.valueOf(classSection.getId()));
    }
    return ids;
  }
}
