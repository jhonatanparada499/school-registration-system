package com.school.app.service;

import com.school.app.model.*;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Methods:
 * 1. findElegibleInstructor(...)
 * 2. createClassSession(...)
 * 3. registerStudent(...)
 */

public class RegistrationService {

  public static List<Instructor> findEligibleInstructors(Course theCourse) {
    List<Instructor> eligibleInstructors = new ArrayList<>();

    // load parsed Instructors.csv data
    Map<String, Instructor> instructors = InstructorService.load();

    for (Instructor instructor : instructors.values()) {
      if (instructor.canTeach(theCourse)) {
        eligibleInstructors.add(instructor);
      }
    }

    return eligibleInstructors;
  }

  // Create rest of method defintions
}
