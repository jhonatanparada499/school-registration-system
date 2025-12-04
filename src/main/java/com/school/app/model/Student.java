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
      String theMajor,
      List<ClassSession> theEnrolledClasses) {
    id = theId;
    name = theName;
    major = theMajor;
    enrolledClasses = theEnrolledClasses;
  }

  public int getCurrentCredits() {
    int currentCredits = 0;
    for (ClassSession classSession : enrolledClasses) {
      currentCredits += classSession.getCourse().getCredits();
    }
    return currentCredits;
  }

  public List<ClassSession> getEnrolledClasses() {
    return new ArrayList<>(enrolledClasses);
  }

  public void addEnrolledClass(ClassSession theClassSession) {
    this.enrolledClasses.add(theClassSession);
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

  public void setEnrolledClasses(List<ClassSession> enrolledClasses) {
    this.enrolledClasses = enrolledClasses;
  }
}


}
