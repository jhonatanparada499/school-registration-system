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
  private Course course;
  private Instructor instructor;
  private Classroom classroom;
  private int sectionNumber;
  private int maxCapacity;
  private List<Student> enrolledStudents;

  // constructor to create a new class section
  public ClassSession(
      int theId,
      Course theCourse,
      Instructor theInstructor,
      Classroom theClassroom,
      int theSectionNumber,
      int theMaxCapacity) {
    id = theId;
    course = theCourse;
    instructor = theInstructor;
    classroom = theClassroom;
    sectionNumber = theSectionNumber;
    maxCapacity = theMaxCapacity;
    enrolledStudents = new ArrayList<>();
  }

  public int getId() {
    return this.id;
  }

  public String getStringId() {
    return String.valueOf(this.id);
  }

  public Course getCourse() {
    return this.course;
  }

  public String getCourseName() {
    return getCourse().getName();
  }

  public String getCourseId() {
    return getCourse().getCourseId();
  }

  // Fix privacy leak in the rest of getter methods
  public Instructor getInstructor() {
    return this.instructor;
  }

  public String getInstructorName() {
    return getInstructor().getName();
  }

  public Classroom getClassroom() {
    return this.classroom;
  }

  public String getClassroomNumber() {
    return getClassroom().getRoomNumber();
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
  public List<Student> getEnrolledStudents() {
    return this.enrolledStudents;
  }

  public String getEnrolledStudentsSeparatedByPipe() {
    String result = "";
    for (Student student : this.enrolledStudents) {
      result += student.getId() + "|";
    }
    return result;
  }

  public String getFormatSectionNumber() {
    return String.format("%03d", getSectionNumber());
  }

  public String getEnrolledCapacity() {
    return String.format(
        "%d / %d", getEnrolledStudents().size(), getMaxCapacity());
  }

  // Method not specifed in project instructions
  public void addEnrolledStudent(Student theStudent) {
    this.enrolledStudents.add(theStudent);
  }

  public boolean isFull() {
    return getEnrolledStudents().size() >= getMaxCapacity();
  }

}
