package com.school.app.model;

import java.util.List;

public class Instructor {
  private String id;
  private String name;
  // contains courses ids
  private List<String> qualifiedCourses;
  // classes currently being taught
  private List<ClassSession> teachingAssignment;

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
}
