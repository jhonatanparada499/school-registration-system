package com.school.app.view.cli;

import java.util.Scanner;

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
        StudentRegistrator.display();
        break;
      case 3:
        MainMenu.display();
        break;

      default:
        break;
    }
    keyboard.close();

  }
}
