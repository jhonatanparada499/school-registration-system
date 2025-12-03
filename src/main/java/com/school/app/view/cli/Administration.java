package com.school.app.view.cli;

import java.util.Scanner;

import java.util.Map;
import java.util.List;

import com.school.app.service.InstructorService;
import com.school.app.service.RegistrationService;
import com.school.app.service.ClassroomService;
import com.school.app.service.CourseService;

import com.school.app.model.Instructor;
import com.school.app.model.ClassSession;
import com.school.app.model.Course;
import com.school.app.model.Classroom;

public class Administration {
  public static void display() {
    int userOption;
    Scanner keyboard = new Scanner(System.in);
    System.out.println("--- Administration Menu ---");
    System.out.println("1. Create Class Section");
    System.out.println("2. Register Student");
    System.out.println("3. Go Back");

    System.out.print("Your choice: ");
    userOption = keyboard.nextInt();
    switch (userOption) {
      case 1:
        ClassSectionCreator.display();
        break;
      case 2:
        System.out.println("Still working in this area");
        System.exit(0);
        break;
      case 3:
        MainMenu.display();
        break;

      default:
        break;
    }
    keyboard.close();

  }

  public class ClassSectionCreator {
    public static void display() {
      System.out.print("Enter Course ID: ");

      Scanner keyboard = new Scanner(System.in);
      String courseIdChoice = keyboard.next();

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
        String instructorIdChoice = keyboard.next();
        Map<String, Instructor> instructors = InstructorService.load();
        Instructor instructorChoice = instructors.get(instructorIdChoice);

        if (instructorChoice.getCurrentLoad() > 9) {
          System.out.println("That instructor surpassed credit limit");
          System.out.println("Please try another one");
          continue;
        }

        validInstructorChoice = true;

        Map<String, Classroom> classrooms = ClassroomService.load();
        System.out.println("List of available classrooms: ");
        System.out.println("Room Number | Has Computer | Has smartboard");

        // print availbe rooms
        for (Classroom classroom : classrooms.values()) {
          System.out.println(
              classroom.getRoomNumber() + " | " +
                  classroom.hasComputer() + " | " +
                  classroom.hasSmartboard());
        }
        System.out.print("Enter Room Number: ");
        String classroomNumberChoice = keyboard.next();
        Classroom classroomChoice = classrooms.get(classroomNumberChoice);

        System.out.print("Enter Classroom Maximum Capacity: ");
        int maxCapacityChoice = keyboard.nextInt();

        RegistrationService newRegistration = new RegistrationService();
        try {
          ClassSession newClassSection = newRegistration.createClassSection(
              courseChoice,
              instructorChoice,
              classroomChoice,
              maxCapacityChoice);
          // Writes the new class section record to ClassSession.csv
          RegistrationService.saveClassSection(newClassSection);
          System.out.println("Success");
        } catch (Exception e) {
          System.out.println(e);
          System.out.println("Failure");
        }

      }
      keyboard.close();

    }
  }
}
