package com.school.app.service;

import com.school.app.model.Instructor;
import com.school.app.model.Course;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class RegistrationService {

  public List<Instructor> findEligibleInstructors(Course theCourse) {
    List<Instructor> eligibleInstructors = new ArrayList<>();

    // get Instructor objects without key from hashmap
    Collection<Instructor> instructors = InstructorService.load().values();

    for (Instructor instructor : instructors) {
      if (instructor.canTeach(theCourse)) {
        eligibleInstructors.add(instructor);
      }
    }

    return eligibleInstructors;
  }
}
