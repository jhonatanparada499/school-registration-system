package com.school.app.model;

import java.util.List;

import java.util.ArrayList;
import java.util.Map;

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
  private List<String> qualifiedCourses; // contains courses ids
  private List<Integer> teachingAssignments; // classes currently being taught

  public Instructor(String theId, String theName,
      List<String> theQualifiedCourses,
      List<Integer> theTeachingAssignments) {
    id = theId;
    name = theName;
    qualifiedCourses = theQualifiedCourses;
    teachingAssignments = theTeachingAssignments;
  }

  public List<String> getQualifiedCourses() {
    return new ArrayList<String>(this.qualifiedCourses);
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public List<Integer> getTeachingAssignments() {
    return this.teachingAssignments;
  }

  // it should be add teaching section
  public void addTeachingAssignment(ClassSession theClassSession) {
    this.teachingAssignments.add(theClassSession.getId());
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setQualifiedCourses(List<String> qualifiedCourses) {
    this.qualifiedCourses = new ArrayList<>(qualifiedCourses);
  }

  public boolean canTeach(Course theCourse) {
    String theCourseId = theCourse.getCourseId();
    return this.qualifiedCourses.contains(theCourseId);
  }

  public int getCurrentLoad(
      Map<Integer, ClassSession> theClassSections,
      Map<String, Course> theCourses) {

    int currentLoad = 0;
    for (Integer classSectionId : this.teachingAssignments) {
      ClassSession classSection = theClassSections.get(classSectionId);
      Course course = theCourses.get(classSection.getCourse());
      currentLoad += course.getCredits();
    }
    return currentLoad;
  }

  public String toString() {
    return this.name;
  }
}
