package com.school.app.model;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;

/**
 * Methods:
 * 1. int getCurrentCredits
 * 2. List<ClassSession> getEnrolledClasses
 * 3. void addEnrolledClass
 * 4. String getId
 * 5. String getName
 * 6. String getMajor
 */

public class Student {
  private String id;
  private String name;
  private String major;

  public Student(String theId, String theName,
      String theMajor) {
    id = theId;
    name = theName;
    major = theMajor;
  }

  // calculates current credits based on classSessions.csv
  public int getCurrentCredits(
      Map<Integer, ClassSession> theClassSections,
      Map<String, Course> theCourses) {

    int totalCredits = 0;
    Map<String, Course> courses = theCourses;

    List<String> enrolledClassesId = this.getEnrolledClasses(
        theClassSections);
    for (String enrolledClassId : enrolledClassesId) {
      if (courses.keySet().contains(enrolledClassId)) {
        Course course = courses.get(enrolledClassId);
        totalCredits += course.getCredits();
      }
    }
    return totalCredits;
  }

  // Determines the enrolled Classes for student based on the
  // classsession.cvs file
  public List<String> getEnrolledClasses(
      Map<Integer, ClassSession> theClassSections) {

    List<String> enrolledClasses = new ArrayList<>();

    for (ClassSession classSection : theClassSections.values()) {
      List<String> enrolledStudents = classSection.getEnrolledStudents();
      for (String studentId : enrolledStudents) {
        if (studentId.equals(this.getId())) {
          enrolledClasses.add(classSection.getCourse());
        }
      }
    }
    return new ArrayList<>(enrolledClasses);
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getMajor() {
    return this.major;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setMajor(String major) {
    this.major = major;
  }

}
