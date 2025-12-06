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

  // SchoolException used by createClassSession
  class SchoolException extends Exception {
    public SchoolException(String theMessage) {
      super(theMessage);
    }
  }

  public static List<Instructor> findEligibleInstructors(Course theCourse) {
    if (theCourse == null) {
      System.out.print("The course is null");
      System.exit(0);
    }

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

  public ClassSession createClassSection(
      String theCourseId,
      String theInstructorId,
      String theClassroomId,
      int theCapacity) throws SchoolException {

    Instructor theInstructor = InstructorService.load().get(theInstructorId);
    Course theCourse = CourseService.load().get(theCourseId);
    // Classroom theClassroom = ClassroomService.load().get(theClassroomId);

    // Preconditions
    if (!theInstructor.canTeach(theCourse)) {
      throw new SchoolException("Instructor load exceeded");
    }

    if (theInstructor.getCurrentLoad() +
        theCourse.getCredits() > 9) {
      throw new SchoolException("Instructor load exceeded");
    }

    // get Classesctions from ClassSection.csv.
    Map<Integer, ClassSession> classSections = ClassSessionService.load();

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

    // The class Section number is the same as the Id
    int newClassSectionNumber = newId;

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

  public static void saveClassSection(ClassSession theClassSection) {
    Path path = Paths.get("data", "ClassSession.csv");
    String filePath = String.valueOf(path);

    File classSessionRecords = new File(filePath);

    // Validate if the class section already exists,
    // if so, remove it because below will be
    // rewritten but with updated values of enrolled
    // students
    // add dependency to maven project

    // true for append, false for overwrite
    try (FileWriter writer = new FileWriter(classSessionRecords, true)) {
      if (classSessionRecords.length() > 0) {
        writer.write(System.lineSeparator());
      }

      writer.write(
          theClassSection.getId() + "," +
              theClassSection.getCourse().getCourseId() + "," +
              theClassSection.getInstructor().getId() + "," +
              theClassSection.getClassroom().getRoomNumber() + "," +
              theClassSection.getSectionNumber() + "," +
              theClassSection.getMaxCapacity() + "," +
              theClassSection.getEnrolledStudents().size());
    } catch (Exception e) {
      System.out.println("This execption message is from SaveClassScection");
      System.out.println(e);
    }
  }

  public static void registerStudent(Student theStudent,
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
    //
    //
    // Work needs to be continued here

    theSection.addEnrolledStudent(theStudent);
    // saveClassSection(theSection);
    theStudent.addEnrolledClass(theSection);
    // create a method called saveStudent()
    // saveStudent(theStudent)

  }

}
