package com.school.app.model;

import java.util.List;

import com.school.app.service.ClassSessionService;
import com.school.app.service.CourseService;

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
  // private List<String> teachingAssignment; // classes currently being taught

  public Instructor(String theId, String theName,
      List<String> theQualifiedCourses) {
    id = theId;
    name = theName;
    qualifiedCourses = theQualifiedCourses;
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
    List<String> teachingAssignment = new ArrayList<>();
    Map<Integer, ClassSession> classSections = ClassSessionService.load();
    for (ClassSession classSection : classSections.values()) {
      if (classSection.getInstructor().equals(this.getId())) {
        teachingAssignment.add(classSection.getCourse());
      }
    }
    return teachingAssignment;
  }

  // Setters
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

  public int getCurrentLoad() {
    int currentLoad = 0;
    Map<String, Course> courses = CourseService.load();
    for (String courseId : this.getTeachingAssignment()) {
      Course course = courses.get(courseId);
      currentLoad += course.getCredits();
    }
    return currentLoad;
  }

  public String toString() {
    return this.name;
  }
}
