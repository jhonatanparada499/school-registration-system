package com.school.app.view.cli;

import java.util.Map;
import java.util.List;

import com.school.app.service.RegistrationService;
import com.school.app.service.ClassroomService;
import com.school.app.service.CourseService;
import com.school.app.service.InstructorService;
import com.school.app.model.Instructor;
import com.school.app.model.ClassSession;
import com.school.app.model.Course;
import com.school.app.model.Classroom;

import java.util.Scanner;

public class ClassSectionCreator {
  public static void display() {
    System.out.print("Enter Course ID: ");

    Scanner keyboard = new Scanner(System.in);
    String courseIdChoice = keyboard.nextLine().trim();

    // get a list of eligibleInstructors for the course choice
    Map<String, Course> courses = CourseService.load();
    Course courseChoice = courses.get(courseIdChoice);
    List<Instructor> eligibleInstructors = RegistrationService.findEligibleInstructors(
        courseChoice);

    // print elebigle instructors ID: Name
    System.out.println("List of available instructors: ");
    System.out.println("ID : Name");
    for (Instructor instructor : eligibleInstructors) {
      // System.out.println(instructor);
      System.out.println(
          instructor.getId() + " : "
              + instructor.getName());
    }

    // get an instructor that meets credit condition based on id choice
    boolean validInstructorChoice = false;
    while (!validInstructorChoice) {
      System.out.print("Enter Instructor ID: ");
      String instructorIdChoice = keyboard.nextLine().trim();

      validInstructorChoice = true;

      System.out.println("List of available classrooms: ");
      System.out.println("Room Number | Has Computer | Has smartboard");

      Map<String, Classroom> classrooms = ClassroomService.load();
      // print availbe rooms
      for (Classroom classroom : classrooms.values()) {
        System.out.println(
            classroom.getRoomNumber() + " | " +
                classroom.hasComputer() + " | " +
                classroom.hasSmartboard());
      }
      System.out.print("Enter Room Number: ");
      String classroomNumberIdChoice = keyboard.next();
      // Classroom classroomChoice = classrooms.get(classroomNumberChoice);

      System.out.print("Enter Classroom Maximum Capacity: ");
      int maxCapacityChoice = keyboard.nextInt();

      RegistrationService newRegistration = new RegistrationService();
      try {
        ClassSession newClassSection = newRegistration.createClassSection(
            courseIdChoice,
            instructorIdChoice,
            classroomNumberIdChoice,
            maxCapacityChoice);
        // Writes the new class section record to ClassSession.csv
        RegistrationService.saveClassSection(newClassSection);
        System.out.println("Success");
      } catch (Exception e) {
        System.out.println("Failure");
        System.out.println(e);
      }

    }
    keyboard.close();

  }
}
