package com.school.app.model;

import java.util.ArrayList;
import java.util.List;

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
  private List<ClassSession> enrolledClasses;

  public Student(String theId, String theName,
      String theMajor) {
    id = theId;
    name = theName;
    major = theMajor;
    enrolledClasses = new ArrayList<>();
  }

  // calculates current credits based on classSessions.csv
  public int getCurrentCredits() {
    int totalCredits = 0;
    for (ClassSession classSection : this.enrolledClasses) {
      Course course = classSection.getCourse();
      totalCredits += course.getCredits();
    }
    return totalCredits;
  }

  // Determines the enrolled Classes for student based on the
  // classsession.cvs file
  public List<ClassSession> getEnrolledClasses() {
    return this.enrolledClasses;
  }

  public void addEnrolledClass(ClassSession theClassSection) {
    this.enrolledClasses.add(theClassSection);
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

  public void setEnrolledClasses(
      List<ClassSession> theEnrolledClasses) {
    this.enrolledClasses = theEnrolledClasses;
  }

}
