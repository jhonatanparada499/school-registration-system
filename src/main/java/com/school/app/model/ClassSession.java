package com.school.app.model;

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

  public Course getCourse() {
    return course;
  }

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

  public boolean isFull() {
    return enrolledStudents.size() >= maxCapacity;
  }
}
