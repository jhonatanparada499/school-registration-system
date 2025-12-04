package com.school.app.service;

import com.school.app.model.*;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;

/**
 * Methods:
 * 1. static findElegibleInstructor(...)
 * 2. non-static createClassSection(...)
 * 3. static saveClassSection(...)
 * 4. non-static registerStudent(...)
 */

public class RegistrationService {

  // SchoolException used by createClassSession
  class SchoolException extends Exception {
    public SchoolException(String theMessage) {
      super(theMessage);
    }
  }

  public static List<Instructor> findEligibleInstructors(Course theCourse) {
    List<Instructor> eligibleInstructors = new ArrayList<>();

    // load parsed Instructors.csv data
    Map<String, Instructor> instructors = InstructorService.load();

    for (Instructor instructor : instructors.values()) {
      if (instructor.canTeach(theCourse)) {
        eligibleInstructors.add(instructor);
      }
    }

    return eligibleInstructors;
  }

  // inconsistency: it would make more sense to create a class session
  public ClassSession createClassSection(
      Course theCourse,
      Instructor theInstructor,
      Classroom theClassroom,
      int theCapacity) throws SchoolException {

    if (!theInstructor.canTeach(theCourse)) {
      throw new SchoolException("Instructor load exceeded");
    }

    if (theInstructor.getCurrentLoad() +
        theCourse.getCredits() > 9) {
      throw new SchoolException("Instructor load exceeded");
    }

    // Create session or section?
    // Incosistency: is theCapacity the max capicity or initial?
    ClassSession classSection = new ClassSession(theCourse,
        theInstructor, theClassroom, theCapacity);

    // Add section to the instructor
    theInstructor.addTeachingAssignment(classSection);

    // Does storing (this) to the system mean to write it to Classroom.csv?
    // It would make sense to have a ClassSession.csv file
    // ...

    // The method is called createClassSection but it returns a ClassSession
    // object? In my opinion, the word section should no be used.
    return new ClassSession(
        theCourse, theInstructor,
        theClassroom, theCapacity);
  }

  public static void saveClassSection(ClassSession theClassSection) {
    String homeDir = "/home/jhonatan/";
    String fileDir = homeDir +
        "Projects/Github/jhonatanparada499/" +
        "school-registration-system/data/";
    String fileName = "ClassSession.csv";
    String filePath = fileDir + fileName;

    File classSessionRecords = new File(filePath);

    List<ClassSession> classSections = new ArrayList<>(
        ClassSessionService.load());

    List<ClassSession> duplicatedClassSections = new ArrayList<>();
    for (ClassSession classSection : classSections) {
      if (classSection.getCourse().getCourseId().equals(theClassSection.getCourse().getCourseId())) {
        duplicatedClassSections.add(classSection);
      }
    }

    if (!duplicatedClassSections.isEmpty()) {
      ClassSession lastClassSection = duplicatedClassSections.get(classSections.size() - 1);
      int lastClassSectionNumber = lastClassSection.getSectionNumber();
      int newSectionNumber = lastClassSectionNumber + 1;
      theClassSection.setSectionNumber(newSectionNumber);
    }

    // true for append, false for overwrite
    try (FileWriter writer = new FileWriter(classSessionRecords, true)) {
      if (classSessionRecords.length() > 0) {
        writer.append(System.lineSeparator());
      }

      writer.append(
          theClassSection.getCourse().getCourseId() + "," +
              theClassSection.getInstructor().getName() + "," +
              theClassSection.getClassroom().getRoomNumber() + "," +
              theClassSection.getSectionNumber() + "," +
              theClassSection.getMaxCapacity() + "," +
              theClassSection.getEnrolledStudents().size());
    } catch (Exception e) {
      System.out.println(e);
      System.out.println("This execption message is from SaveClassScection");
    }
  }

  public void registerStudent(Student theStudent,
      ClassSession theSection) {

    // preconditions: 1. Student is not it the class, 2. the "section"
    // is not full, and the credits are below 18
    if (theSection.getEnrolledStudents().contains(theStudent)) {
      System.out.println("The student is already in the class");
      return;
    }
    if (theSection.isFull()) {
      // Could throw an exception
      System.out.println("The class session is full");
      return;
    }

    if (theStudent.getCurrentCredits() +
        theSection.getCourse().getCredits() > 18) {
      System.out.println("Student and Course credits surpass 18");
      return;
    }

    theSection.addEnrolledStudent(theStudent);
    theStudent.addEnrolledClass(theSection);
  }

}
