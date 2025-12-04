# School Registration System
A desktop administrative application for a college registrar.

## Task Distribution Table  

### Models
| Task | Assigned To | Status |
| ------------- | -------------- | -------------- |
| Course.java | Isaias | Completed |
| Classroom.java | Camille | Completed |
| Student.java | Jhonatan | Completed |
| ClassSession.java | Jhonatan | Completed |
| Instructor.java | Jhonatan | Completed |

### Services
| Task | Assigned To | Status |
| ------------- | -------------- | -------------- |
| InstructorService.java | Jhonatan | Completed |
| RegistrationService.java | Jhonatan | Completed |
| CourseService.java | Isaias | In Progress |
| ClassroomService.java | Camille | In Progress |
| ClassSessionService.java | Jhonatan | Completed |

### Setters
| Task | Assigned To | Status |
| ------------- | -------------- | -------------- |
| Define setters for Student.java | Camille | In Progress |
| Define setters for Instructor.java | Isaias | In Progress |

## Project File Structure

```
school-registration-system
├── data
│   ├── Classroom.csv
│   ├── Course.csv
│   ├── Instructor.csv
│   └── Student.csv
└── src
    └── main
        └── java
            └── com
                └── school
                    └── app
                        ├── model
                        │    ├── ClassSession.java
                        │    ├── Classroom.java
                        │    ├── Course.java
                        │    ├── Instructor.java
                        │    └── Student.java
                        ├── service
                        │    ├── ClassroomService.java
                        │    ├── RegistrationService.java
                        │    ├── CourseService.java
                        │    ├── InstructorService.java
                        │    └── StudentService.java
                        ├── view
                        │    └── cli
                        │        ├── Administration.java
                        │        ├── Dashboard.java
                        │        └── MainMenu.java
                        └── Main.java
```

