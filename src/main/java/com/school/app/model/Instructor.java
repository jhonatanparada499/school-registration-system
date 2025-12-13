package com.school.app.model;

import java.util.List;

import java.util.ArrayList;

/**
 * Methods:
 * 1: boolean canTeach
 * 2: int getCurrentLoad
 * 3: void addTeachingAssignment
 *
 */

public class Instructor {
  private String id;
  private String name;
  private List<Course> qualifiedCourses; // contains courses ids
  private List<ClassSession> teachingAssignments; // classes currently being taught

  public Instructor(String theId,
      String theName,
      List<Course> theQualifiedCourses) {
    id = theId;
    name = theName;
    qualifiedCourses = theQualifiedCourses;
    teachingAssignments = new ArrayList<>();
  }

  // public List<String> getQualifiedCourses() {
  // return new ArrayList<Course>(this.qualifiedCourses);
  // }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public List<ClassSession> getTeachingAssignments() {
    return this.teachingAssignments;
  }

  // it should be add teaching section
  public void addTeachingAssignment(ClassSession theClassSession) {
    this.teachingAssignments.add(theClassSession);
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setQualifiedCourses(List<Course> theQualifiedCourses) {
    this.qualifiedCourses = theQualifiedCourses;
  }

  public boolean canTeach(Course theCourse) {
    return this.qualifiedCourses.contains(theCourse);
  }

  public int getCurrentLoad() {
    int currentLoad = 0;
    for (ClassSession classSection : this.teachingAssignments) {
      Course course = classSection.getCourse();
      currentLoad += course.getCredits();
    }
    return currentLoad;
  }

  public String toString() {
    return this.name;
  }
}
