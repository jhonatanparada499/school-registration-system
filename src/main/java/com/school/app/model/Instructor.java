package com.school.app.model;

import java.util.List;

public class Instructor {
  private String id;
  private String name;
  private List<String> qualifiedCourses; // contains courses ids
  private List<ClassSession> teachingAssignment; // classes currently being taught

  public Instructor(String theId, String theName,
      List<String> theQualifiedCourses) {
    this(
        theId, theName,
        theQualifiedCourses, null);
  }

  public Instructor(String theId, String theName,
      List<String> theQualifiedCourses,
      List<ClassSession> theTeachingAssignment) {
    id = theId;
    name = theName;
    qualifiedCourses = theQualifiedCourses;
    teachingAssignment = theTeachingAssignment;
  }

  public boolean canTeach(Course theCourse) {
    String theCourseId = theCourse.getCourseId();
    return this.qualifiedCourses.contains(theCourseId);
  }

  public int getCurrentLoad() {
    int currentLoad = 0;
    for (ClassSession classSession : teachingAssignment) {
      currentLoad += classSession.getCourse().getCredits();
    }
    return currentLoad;
  }

  // method not specified if lab instructions
  public void addTeachingAssignment(ClassSession theClassSession) {
    this.teachingAssignment.add(theClassSession);
  }

  @Override
  public String toString() {
    return this.name;
  }

}
