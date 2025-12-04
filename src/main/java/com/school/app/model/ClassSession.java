package com.school.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Methods:
 * 1. void addEnrolledStudents
 * 2. boolean isFull()
 */

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
      int theSectionNumber,
      int theMaxCapacity,
      int theEnrolledStudents) {
    course = theCourse;
    instructor = theInstructor;
    classroom = theClassroom;
    sectionNumber = theSectionNumber;
    maxCapacity = theMaxCapacity;
    enrolledStudents = new ArrayList<>(
        theEnrolledStudents);
  }

  public ClassSession(Course theCourse,
      Instructor theInstructor,
      Classroom theClassroom,
      int theMaxCapacity) {
    course = theCourse;
    instructor = theInstructor;
    classroom = theClassroom;
    sectionNumber = 1;
    maxCapacity = theMaxCapacity;
    enrolledStudents = new ArrayList<>();
  }

  public Course getCourse() {
    return new Course(this.course);
  }

  // Fix privacy leak in the rest of getter methods
  public Instructor getInstructor() {
    return new Instructor(this.instructor);
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

  public void setSectionNumber(int theSectionNumber) {
    this.sectionNumber = theSectionNumber;
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
