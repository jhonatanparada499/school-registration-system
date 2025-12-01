package com.school.app.model;

import java.util.ArrayList;
import java.util.List;

public class ClassSession {
  private Course course;
  private Instructor instructor;
  private Classroom classroom;
  private int sectionNumber;
  private int maxCapacity;
  private List<Student> enrolledStudents;

  public ClassSession(Course theCourse,
      Instructor theInstructor,
      Classroom theClassroom,
      int theSectionNumber,
      int theMaxCapacity,
      List<Student> theEnrolledStudents) {
    course = theCourse;
    instructor = theInstructor;
    classroom = theClassroom;
    sectionNumber = theSectionNumber;
    maxCapacity = theMaxCapacity;
    enrolledStudents = theEnrolledStudents;
  }

  public ClassSession(Course theCourse,
      Instructor theInstructor,
      Classroom theClassroom,
      int theMaxCapacity) {
    this(theCourse, theInstructor,
        theClassroom, 0,
        theMaxCapacity, null);
  }

  public Course getCourse() {
    return new Course(this.course);
  }

  // Fix privacy leak in the rest of getter methods
  public Instructor getInstructor() {
    return instructor;
  }

  public Classroom getClassroom() {
    return classroom;
  }

  public int getMaxCapacity() {
    return maxCapacity;
  }

  public int getSectionNumber() {
    return sectionNumber;
  }

  // Method not specified in project instructions
  // This method is similar to the one in Student.java
  public List<Student> getEnrolledStudents() {
    return new ArrayList<>(enrolledStudents);
  }

  // Method not specifed in project instructions
  public void addEnrolledStudent(Student theStudent) {
    this.enrolledStudents.add(theStudent);
  }

  public boolean isFull() {
    return this.enrolledStudents.size() >= this.maxCapacity;
  }

}
