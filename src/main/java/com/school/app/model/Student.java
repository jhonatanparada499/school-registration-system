package com.school.app.model;

import java.util.ArrayList;
import java.util.List;

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

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getMajor() {
    return this.major;
  }

}
