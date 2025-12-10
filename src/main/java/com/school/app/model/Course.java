package com.school.app.model;

public class Course {
  private String courseId;
  private String name;
  private int credits;

  public Course(String theCourseId,
      String theName, int theCredits) {
    courseId = theCourseId;
    name = theName;
    credits = theCredits;
  }

  public Course(Course otherCourse) {
    this(otherCourse.courseId,
        otherCourse.name,
        otherCourse.credits);
  }

  public int getCredits() {
    return this.credits;
  }
  //Setters
  public void setCourseId(String courseId) {
      this.courseId = courseId;
  }

  public void setName(String name) {
        this.name = name;
    }

  public void setCredits(int credits) {
        this.credits = credits;
    }

  public String getCourseId() {
    return this.courseId;
  }
  
  public String getName(){
    return this.name;
  }
}
