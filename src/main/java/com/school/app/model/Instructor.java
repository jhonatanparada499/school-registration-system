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
  private List<String> qualifiedCourses; // contains courses ids
  private List<String> teachingAssignment; // classes currently being taught

  public Instructor(String theId, String theName,
      List<String> theQualifiedCourses) {
    id = theId;
    name = theName;
    qualifiedCourses = theQualifiedCourses;
    teachingAssignment = new ArrayList<>();
  }

  public Instructor(String theId, String theName,
      List<String> theQualifiedCourses,
      List<String> theTeachingAssignment) {
    id = theId;
    name = theName;
    qualifiedCourses = theQualifiedCourses;
    teachingAssignment = theTeachingAssignment;
  }

  public Instructor(Instructor theInstructor) {
    id = theInstructor.getId();
    name = theInstructor.getName();
    qualifiedCourses = theInstructor.getQualifiedCourses();
    teachingAssignment = theInstructor.getTeachingAssignment();
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

  public List<String> getTeachingAssignment() {
    return new ArrayList<>(this.teachingAssignment);
  }
    //Setters
   public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setQualifiedCourses(List<String> qualifiedCourses) {
    this.qualifiedCourses = new ArrayList<>(qualifiedCourses);
  }

  public void setTeachingAssignment(List<ClassSession> teachingAssignment) {
    this.teachingAssignment = new ArrayList<>(teachingAssignment);
  }
  
   public boolean canTeach(Course theCourse) {
    String theCourseId = theCourse.getCourseId();
    return this.qualifiedCourses.contains(theCourseId);
  }

  public int getCurrentLoad() {
    // int currentLoad = 0;
    // for (ClassSession classSession : this.teachingAssignment) {
    // currentLoad += classSession.getCourse().getCredits();
    // }
    // return currentLoad;
    System.out.println("still working in get current load");
    return 0;
  }

  // method not specified if lab instructions
  // public void addTeachingAssignment(ClassSession theClassSession) {
  // this.teachingAssignment.add(theClassSession);
  // }

  public String toString() {
    return this.name;
  }
}
