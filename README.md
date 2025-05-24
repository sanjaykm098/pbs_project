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

## How to Run
1. Clone or download the repository.
2. Open the project in your preferred Java IDE.
3. Compile all `.java` files.
4. Run `PBS_Project.java` to start the application.

## Usage
- Follow the on-screen menu to manage patients, physiotherapists, and appointments.
- All data is stored in memory and will be lost when the application is closed.

## Future Enhancements
- Persistent storage using a database or file system
- Web or mobile interface
- User authentication and role-based access
- Notification system for appointments

## License
This project is licensed under the MIT License.
