# Physiotherapy Booking System (PBS)

## Overview
The Physiotherapy Booking System (PBS) is a Java console-based application designed to help physiotherapy clinics manage patients, physiotherapists, appointments, and generate reports. All data is managed in memory for simplicity and speed.

## Features
- Add, update, remove, and view patient records
- Add, update, remove, and view physiotherapist records
- Book, reschedule, cancel, and view appointments
- Generate various reports (appointment summaries, performance, etc.)
- Input validation and error handling

## Project Structure
```
PBS_Project/
├── LICENSE
├── README.md
├── PBS_Project.java
├── manager/
│   ├── AppointmentManager.java
│   ├── PatientManager.java
│   └── PhysiotherapistManager.java
├── models/
│   ├── Appointment.java
│   ├── Patient.java
│   ├── Physiotherapist.java
│   └── Treatment.java
├── services/
│   ├── BookingSystem.java
│   └── ReportGenerator.java
└── utils/
    └── Validator.java
```

## Requirements
- Java 17 or higher
- Any OS (Windows, Linux, macOS)
- IDE (e.g., Visual Studio Code, IntelliJ IDEA, Eclipse)

## How to Run the Project

1. Ensure you have Java 17 or higher installed on your system.
2. Open the project folder in your preferred Java IDE (such as Visual Studio Code, IntelliJ IDEA, or Eclipse).
3. Locate the `PBS_Project.java` file in the root directory.
4. Compile all Java files in the project. In most IDEs, this happens automatically when you run the main file.
5. Run the `PBS_Project.java` file. This will launch the console-based application.
6. Follow the on-screen menu instructions to manage patients, physiotherapists, appointments, and reports.

**Note:** All data is stored in memory and will be lost when the application is closed.

## Run Command

If you prefer to run the project from the command line, use the following commands:

1. Open Command Prompt and navigate to the project directory:
   ```sh
   cd path\to\PBS_Project\src\pbs_project
   ```
2. Compile all Java files:
   ```sh
   javac *.java manager/*.java models/*.java services/*.java utils/*.java
   ```
3. Run the main class:
   ```sh
   java PBS_Project
   ```

## Usage
- Follow the on-screen menu to manage patients, physiotherapists, and appointments.
- All data is stored in memory and will be lost when the application is closed.

## Future Enhancements
- Persistent storage using a database or file system
- Web or mobile interface
- User authentication and role-based access
- Notification system for appointments

## Author
Sanjay Kumar - [GitHub Profile](https://github.com/sanjaykm098)

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments
- Thanks to the open-source community for their contributions and support.
## Contributing
- Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## Contact
For any questions or feedback, please contact me at [sanjaykm.live@gmail.com](mailto:sanjaykm.live@gmail.com)

## Issues
If you encounter any issues while running the project, please check the following:
- Ensure you have the correct version of Java installed.
- Make sure all files are compiled without errors. 
- If you have any specific errors, please open an issue on the GitHub repository with details about the error message and your environment.

