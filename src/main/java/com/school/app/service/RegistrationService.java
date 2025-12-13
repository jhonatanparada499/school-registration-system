package com.school.app.service;

import com.school.app.model.*;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Methods:
 * 1. static findElegibleInstructor(...)
 * 2. non-static createClassSection(...)
 * 3. static saveClassSection(...)
 * 4. non-static registerStudent(...)
 */

public class RegistrationService {

  private Map<Integer, ClassSession> classSections;
  private Map<String, Course> courses;
  // private Map<String, Classroom> classrooms;
  // private Map<String, Student> students;
  private Map<String, Instructor> instructors;

  public RegistrationService(
      Map<Integer, ClassSession> theClassSections,
      Map<String, Course> theCourses,
      // Map<String, Classroom> theClassrooms,
      // Map<String, Student> theStudents,
      Map<String, Instructor> theInstructors) {
    classSections = theClassSections;
    courses = theCourses;
    // classrooms = theClassrooms;
    // students = theStudents;
    instructors = theInstructors;
  }

  // SchoolException used by createClassSession
  class SchoolException extends Exception {
    public SchoolException(String theMessage) {
      super(theMessage);
    }
  }

  public List<Instructor> findEligibleInstructors(
      Course theCourse) {

    if (theCourse == null) {
      System.out.print("The course is null");
      return null;
    }

    List<Instructor> eligibleInstructors = new ArrayList<>();

    for (Instructor instructor : instructors.values()) {
      if (instructor.canTeach(theCourse)) {
        eligibleInstructors.add(instructor);
      }
    }

    return eligibleInstructors;
  }

  public ClassSession createClassSection(
      String theCourseId,
      String theInstructorId,
      String theClassroomId,
      int theCapacity) throws SchoolException {

    Instructor theInstructor = instructors.get(theInstructorId);
    Course theCourse = courses.get(theCourseId);

    if (!theInstructor.canTeach(theCourse)) {
      throw new SchoolException("Instructor cannot teach that course.");
    }

    if (theInstructor.getCurrentLoad(
        classSections,
        courses) +
        theCourse.getCredits() > 9) {
      String message = theInstructor.getName() +
          " has reached the maximum teaching load.";
      throw new SchoolException(message);
    }

    // Get the class section with greatest id value and Calculate new id
    int greatestClassSectionId = 0;
    for (Integer id : classSections.keySet()) {
      if (id > greatestClassSectionId) {
        greatestClassSectionId = id;
      }
    }
    int newId = greatestClassSectionId + 1;

    // Check to see if the course id is already in the db
    // if so, increment the section number
    List<ClassSession> duplicatedclassSections = new ArrayList<>();
    for (ClassSession classSection : classSections.values()) {
      if (classSection.getCourse().equals(theCourse.getCourseId())) {
        duplicatedclassSections.add(classSection);
      }
    }

    // The class Section number is calculated based on duplicates
    // if not duplicates the default is 1
    int newClassSectionNumber = duplicatedclassSections.size() + 1;

    // When creating a new class section, it makes sense that it has
    // zero students enrolled
    return new ClassSession(
        newId,
        theCourseId,
        theInstructorId,
        theClassroomId,
        newClassSectionNumber,
        theCapacity);
  }

  public void writeToClassSections() {
    Path path = Paths.get("data", "ClassSession.csv");
    String filePath = String.valueOf(path);
    File classSectionRecords = new File(filePath);

    // false for overwrite
    try (FileWriter writer = new FileWriter(classSectionRecords, false)) {
      for (ClassSession classSection : classSections.values()) {
        writer.write(
            classSection.getId() + "," +
                classSection.getCourse() + "," +
                classSection.getInstructor() + "," +
                classSection.getClassroom() + "," +
                classSection.getSectionNumber() + "," +
                classSection.getMaxCapacity() + "," +
                classSection.getEnrolledStudentsSeparatedByPipe() + " ");
        writer.write(System.lineSeparator());
      }
    } catch (Exception e) {
      System.out.println("Error from first try statement in saveClassSection");
      System.out.println(e);
    }
  }

  public void registerStudent(Student theStudent,
      ClassSession theSection) throws SchoolException {

    // work needs to be done here
    // preconditions: 1. Student is not it the class, 2. the "section"
    // is not full, and the credits are below 18
    if (theSection.getEnrolledStudents().contains(theStudent.getId())) {
      throw new SchoolException("Error: The student is already in the class.");
    }
    if (theSection.isFull()) {
      // Could throw an exception
      throw new SchoolException("The class session is full");
    }

    Course sectionCourse = courses.get(theSection.getCourse());

    if (theStudent.getCurrentCredits(
        classSections,
        courses) +
        sectionCourse.getCredits() > 18) {
      throw new SchoolException(
          "Registration would exceed maximum semester credits (18).");
    }

    // adds the student id to enrolled students of class section
    theSection.addEnrolledStudent(theStudent);
  }

}
