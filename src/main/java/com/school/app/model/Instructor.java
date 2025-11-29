package com.school.app.model;

import java.util.List;

public class Instructor {
  private String id;
  private String name;
  // contains courses ids
  private List<String> qualifiedCourses;
  // classes currently being taught
  private List<ClassSession> teachingAssignment;

}
