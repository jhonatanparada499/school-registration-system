package com.school.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Methods:
 * 1. void addEnrolledStudents
 * 2. boolean isFull()
 */

public class ClassSession {
  private int id;
  private String course;
  private String instructor;
  private String classroom;
  private int sectionNumber;
  private int maxCapacity;
  private List<String> enrolledIdStudents;

  // constructor to create a new class section
  public ClassSession(
      int theId,
      String theCourse,
      String theInstructor,
      String theClassroom,
      int theSectionNumber,
      int theMaxCapacity) {
    id = theId;
    course = theCourse;
    instructor = theInstructor;
    classroom = theClassroom;
    sectionNumber = theSectionNumber;
    maxCapacity = theMaxCapacity;
    enrolledIdStudents = new ArrayList<>();
  }

  // constructor used to compile existing records
  public ClassSession(
      int theId,
      String theCourse,
      String theInstructor,
      String theClassroom,
      int theSectionNumber,
      int theMaxCapacity,
      List<String> theEnrolledStudents) {
    id = theId;
    course = theCourse;
    instructor = theInstructor;
    classroom = theClassroom;
    sectionNumber = theSectionNumber;
    maxCapacity = theMaxCapacity;
    enrolledIdStudents = theEnrolledStudents;
  }

  public int getId() {
    return this.id;
  }

  public String getStringId() {
    return String.valueOf(this.id);
  }

  public String getCourse() {
    return this.course;
  }

  // Fix privacy leak in the rest of getter methods
  public String getInstructor() {
    return this.instructor;
  }

  public String getClassroom() {
    return classroom;
  }

  public int getMaxCapacity() {
    return maxCapacity;
  }

  public int getSectionNumber() {
    return sectionNumber;
  }

  public void setId(int theId) {
    this.id = theId;
  }

  public void setSectionNumber(int theSectionNumber) {
    this.sectionNumber = theSectionNumber;
  }

  // Method not specified in project instructions
  // This method is similar to the one in Student.java
  public List<String> getEnrolledStudents() {
    return new ArrayList<>(enrolledIdStudents);
  }

  public String getEnrolledStudentsSeparatedByPipe() {
    String result = "";
    for (String studentId : enrolledIdStudents) {
      result += studentId + "|";
    }
    return result;
  }

  public String getFormatSectionNumber() {
    return String.format("%03d", this.sectionNumber);
  }

  public String getEnrolledCapacity() {
    return String.format(
        "%d / %d", this.enrolledIdStudents.size(), this.maxCapacity);
  }

  // Method not specifed in project instructions
  public void addEnrolledStudent(Student theStudent) {
    this.enrolledIdStudents.add(theStudent.getId());
  }

  public boolean isFull() {
    return this.enrolledIdStudents.size() >= this.maxCapacity;
  }

}
