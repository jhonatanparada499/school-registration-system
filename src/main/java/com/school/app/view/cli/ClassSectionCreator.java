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
    boolean completedForm = false;
    while (!completedForm) {

      System.out.print("Enter Course ID: ");

      Scanner keyboard = new Scanner(System.in);
      String courseIdChoice = keyboard.nextLine().trim();

      // get a list of eligibleInstructors for the course choice
      Map<String, Course> courses = CourseService.load();
      Course courseChoice = courses.get(courseIdChoice);

      if (courseChoice == null) {
        System.out.println("Course Id was not found in database, try again");
        continue;
      }

      List<Instructor> eligibleInstructors = RegistrationService.findEligibleInstructors(
          courseChoice);

      if (eligibleInstructors.size() < 1) {
        System.out.println("An instructor for this course has not been assigned");
        System.out.println("Try another course");
        continue;
      }

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

        Map<String, Instructor> instructors = InstructorService.load();
        if (instructors.get(instructorIdChoice) == null) {
          System.out.println("Instructor not found, try again:");
          continue;
        }
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

        boolean validClassroomChoice = false;
        while (!validClassroomChoice) {
          System.out.print("Enter Room Number: ");
          String classroomNumberIdChoice = keyboard.nextLine().trim();

          if (classrooms.get(classroomNumberIdChoice) == null) {
            System.out.println("Classroom number found, try again");
            continue;
          }
          validClassroomChoice = true;

          boolean validMaximumCapacity = false;
          while (!validMaximumCapacity) {
            System.out.print("Enter Classroom Maximum Capacity: ");

            String stringMaxCapacityChoice = keyboard.nextLine().trim();
            // exception to handle integers
            try {
              Integer.parseInt(stringMaxCapacityChoice);
            } catch (Exception e) {
              System.out.println("No an integer, try again");
              continue;
            }

            int maxCapacityChoice = Integer.parseInt(stringMaxCapacityChoice);

            if (maxCapacityChoice < 1) {
              System.out.println("Do not be funny, try again");
              continue;
            }
            validMaximumCapacity = true;

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
              completedForm = true;
            } catch (Exception e) {
              System.out.println(e.getMessage());
              System.out.println("Please Try again");
              continue;
            }
            System.out.println("Class section was successfully created");
            Administration.display();
          }
        }
      }
    }
  }
}
