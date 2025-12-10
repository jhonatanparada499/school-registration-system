![Closed Pull Requests](https://img.shields.io/github/issues-pr-closed/jhonatanparada499/school-registration-system)
![Open Pull Requests](https://img.shields.io/github/issues-pr/jhonatanparada499/school-registration-system)
![Contributors](https://img.shields.io/github/contributors/jhonatanparada499/school-registration-system)

# School Registration System
A desktop administrative application for a college registrar.

## Usage

To run the CLI application, use this command:

```bash
bash compile-and-run.sh
```

To run the GUI application, install Apache Maven and use this command:

```bash
mvn clean javafx:run
```

## Project File Structure

```
school-registration-system
├── data
│   ├── Classroom.csv
│   ├── ClassSession.csv
│   ├── Course.csv
│   ├── Instructor.csv
│   └── Student.csv
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── school
    │   │           └── app
    │   │               ├── controller
    │   │               │    ├── AdministrationSceneController.java
    │   │               │    ├── DashboardSceneController.java
    │   │               │    ├── StudentSceneController.java
    │   │               │    ├── FilterableComboBox.java
    │   │               │    └── MainSceneController.java
    │   │               ├── model
    │   │               │    ├── ClassSession.java
    │   │               │    ├── Classroom.java
    │   │               │    ├── Course.java
    │   │               │    ├── Instructor.java
    │   │               │    └── Student.java
    │   │               ├── service
    │   │               │    ├── ClassroomService.java
    │   │               │    ├── RegistrationService.java
    │   │               │    ├── CourseService.java
    │   │               │    ├── InstructorService.java
    │   │               │    └── StudentService.java
    │   │               ├── view
    │   │               │    └── cli
    │   │               │        ├── Administration.java
    │   │               │        ├── ClassSectionCreator.java
    │   │               │        ├── StudentRegistrator.java
    │   │               │        ├── Dashboard.java
    │   │               │        └── MainMenu.java
    │   │               ├── Main.java
    │   │               └── MainCLI.java
    │   └── module-info.java
    └── resources
        └── com
            └── school
                └── app
                    └── view
                        ├── administrationScene.fxml
                        ├── dashboardScene.fxml
                        ├── studentScene.fxml
                        └── mainScene.fxml
```

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
| CourseService.java | Isaias | Completed |
| ClassroomService.java | Camille | Completed |
| ClassSessionService.java | Jhonatan | Completed |
| StudentService.java | Jhonatan | Completed |

### Setters
| Task | Assigned To | Status |
| ------------- | -------------- | -------------- |
| Define setters for Student.java | Camille | Completed |
| Define setters for Instructor.java | Isaias | Completed |
| Define setters for Course.java | Isaias | In Progress |

### Controllers
| Task | Assigned To | Status |
| ------------- | -------------- | -------------- |
| MainSceneController.java | Jhonatan | Completed |
| AdministrationSceneController.java | Jhonatan | Completed |
| DashboardSceneController.java | Jhonatan | Completed |


### CLI View
| Task | Assigned To | Status |
| ------------- | -------------- | -------------- |
| Dashboard.java | Camille | Completed |
| Administration.java | Jhonatan | Completed |
| MainMenu.java | Jhonatan | Completed |
| ClassSectionCreator.java | Jhonatan | Completed |
| StudentRegistrator.java | Jhonatan | Completed |

### GUI View
| Task | Assigned To | Status |
| ------------- | -------------- | -------------- |
| administrationScene.fxml | Jhonatan | Completed |
| dashboardScene.fxml | Jhonatan | Completed |
| studentScene.fxml | Jhonatan | Completed |

