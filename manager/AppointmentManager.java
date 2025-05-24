package pbs_project.manager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import pbs_project.models.Appointment;
import pbs_project.models.Patient;
import pbs_project.models.Physiotherapist;
import pbs_project.models.Treatment;
import pbs_project.services.BookingSystem;
import pbs_project.utils.Validator;

public class AppointmentManager {

    public Scanner scanner;
    private PatientManager pManager;
    private PhysiotherapistManager physioManager;
    private BookingSystem bSystem;
    private List<Appointment> appointments;
    private List<Treatment> treatments;
    private Validator validator;
    private List<Patient> patients;
    private List<Physiotherapist> physiotherapists;

    //Create constructor
    public AppointmentManager(BookingSystem bSystem, PatientManager pManager, PhysiotherapistManager physioManager) {
        this.scanner = new Scanner(System.in);
        this.validator = new Validator();
        this.pManager = pManager;
        this.physioManager = physioManager;
        this.bSystem = bSystem;
        this.patients = bSystem.getPatients();
        this.appointments = bSystem.getAppointments();
        this.treatments = bSystem.getTreatments();
        this.physiotherapists = bSystem.getPhysiotherapists();

    }

    // Create appointment management functionality
    public void appointmentManagement() {
        String choice = "";
        String option = "";
        System.out.println("\nAppointment Management\n================================");
        while (!choice.equals("7")) {
            System.out.println("""
                       
                       1. View All Treatments
                       2. Book Appointment 
                       3. Change Appointment
                       4. Cancel Appointment
                       5. Attend Appointment
                       6. View All Appointments
                       7. Back To Previous Menu""");
            System.out.print("Enter Your Choice : ");
            choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                showTreatments(treatments);
                if (!treatments.isEmpty()) {
                    while (true) {
                        System.out.print("Do you want to look up treatments by physiotherapist's name? (Yes/No): ");
                        option = scanner.nextLine().trim();

                        if (option.equalsIgnoreCase("yes") || option.equalsIgnoreCase("y")) {
                            // Get filter treatments
                            List<Treatment> filterTreatments = bSystem.filterTreatments(physioManager);
                            if (!filterTreatments.isEmpty()) {
                                showTreatments(filterTreatments);
                            }
                        } else {
                            break;
                        }
                    }
                }
            } else if (choice.equals("2")) {
                // Get result
                boolean results = bookAppointments();
                if (results) {
                    System.out.println("Success! Appointment Booked Successfully.");
                }
            } else if (choice.equals("3")) {
                // Get result
                boolean results = changeAppointments();
                if (results) {
                    System.out.println("Success! Appointment Changed Successfully.");
                }
            } else if (choice.equals("4")) {
                // Get result
                boolean results = cancelAppointments();
                if (results) {
                    System.out.println("Success! Appointment Canceled Successfully.");
                }
            } else if (choice.equals("5")) {
                // Get result
                boolean results = attendAppointments();
                if (results) {
                    System.out.println("Success! Appointment Attended Successfully.");
                }
            } else if (choice.equals("6")) {
                showAppointments();
            } else {
                System.out.println("Return to the previous menu.");
            }
        }
    }

    //Show all timetable 
    public void showTreatments(List<Treatment> treatments) {
        if (!treatments.isEmpty()) {
            System.out.println("\nAll Treatments Lists");
            System.out.println("_________________________________________________________________________________________________________________________________________________________________________________");
            System.out.printf("| %-12s | %-25s | %-20s | %-30s | %-15s | %-15s | %-20s | %-15s |\n", "Treatment ID", "Treatment Name", "Physiotherapist Name", "Physiotherapist Expertise", "Date", "Day", "Time", "Status");
            System.out.println("_________________________________________________________________________________________________________________________________________________________________________________");
            for (Treatment treatment : treatments) {
                Physiotherapist physio = physioManager.getPhysioByID(treatment.getPhysioID());
                if (physio == null) {
                    System.err.printf("Error: Physiotherapist with ID %d not found.\n", treatment.getPhysioID());
                    continue; // Skip this treatment if the physiotherapist is not found
                }
                System.out.printf("| %-12s | %-25s | %-20s | %-30s | %-15s | %-15s | %-20s | %-15s |\n",
                        "T_000" + treatment.gettID(),
                        treatment.getTreatmentName(),
                        physio.getFirstname() + " " + physio.getLastname(),
                        String.join(" | ", physio.getExpertise()),
                        treatment.getDate(),
                        treatment.getDay(),
                        treatment.getTime(),
                        treatment.getStatus());
            }
            System.out.println("_________________________________________________________________________________________________________________________________________________________________________________");
        } else {
            System.err.println("Sorry! Treatment data not found.");
        }
    }

    //Book appointment
    public boolean bookAppointments() {
        int appointmentID = appointments.size() + 1;
        boolean appointmentBooked = false;
        String tid;
        String pid;
        //show all treatment details
        showTreatments(treatments);
        while (!appointmentBooked) {
            //Ask treatment id to book appointment
            System.out.print("Enter Treatment Id To Book Appointment : ");
            tid = scanner.nextLine().trim();
            if (tid.equalsIgnoreCase("close")) {
                //Close form
                System.out.println("Closing the form.");
                return false;
            } else if (!validator.isValidID(tid)) {
                System.err.println("Invalid Treatment ID ! Pleae enter a valid treatment id or type 'close' to exit the form.");
            } else {
                int treatmentID = Integer.parseInt(tid);
                if (!validator.isTreatmentIdExist(treatmentID, treatments)) {
                    System.err.println("Treatment ID does not exist ! Please enter valid treatment id or type 'close' to exit the form.");
                } else if (!validator.isTreatmentAvailable(treatmentID, treatments)) {
                    System.err.println("This treatment has been already booked ! Please enter another treatment id or type 'close' to exit the form.");
                } else {
                    //Show all Patient list 
                    pManager.showPatients();
                    while (!appointmentBooked) {
                        System.out.print("Enter Patient ID To Book Appointment : ");
                        pid = scanner.nextLine().trim();
                        if (!validator.isValidID(pid)) {
                            System.err.println("Invalid Patient ID ! Pleae enter a valid patient id or type 'close' to exit the form.");
                        } else {
                            int patientID = Integer.parseInt(pid);
                            if (!validator.isExistPatientID(patientID, patients)) {
                                System.err.println("Patient ID does not exist ! Please enter valid patient id or type 'close' to exit the form.");
                            } else {
                                //Store book appoint in list
                                appointments.add(new Appointment(appointmentID, patientID, treatmentID, LocalDateTime.now(), "Booked"));
                                //Update treatment status
                                //Get treatement by id 
                                getTreatmentByID(treatmentID).setStatus("Booked");
                                //Set appointment booked true
                                appointmentBooked = true;
                            }
                        }
                    }
                }
            }
        }

        return appointmentBooked;

    }

    // Change appointment
    public boolean changeAppointments() {
        boolean appointmentChanged = false;
        String aid;
        String tid = "";
        String pid = "";
        int treatmentID = 0;
        int patientID = 0;

        // Show all appointment list 
        showAppointments();
        if (!appointments.isEmpty()) {
            while (!appointmentChanged) {
                System.out.print("Enter Appointment ID To Change: ");
                aid = scanner.nextLine().trim();
                if (aid.equalsIgnoreCase("close")) {
                    // Close form
                    System.out.println("Closing the form.");
                    return false;
                } else if (!validator.isValidID(aid)) {
                    System.err.println("Invalid Appointment ID! Please enter a valid appointment ID or type 'close' to exit the form.");
                } else {
                    int appointmentID = Integer.parseInt(aid);

                    // Validate appointment ID 
                    if (!validator.isExistAppointmentID(appointmentID, appointments)) {
                        System.err.println("Appointment ID does not exist! Please enter a valid appointment ID or type 'close' to exit the form.");
                    } else if (validator.isAppointmentAttendOrCancel(appointmentID, appointments)) {
                        System.err.println("Appointment ID has already been attended or canceled! Please enter another appointment ID or type 'close' to exit the form.");
                    } else {

                        // Show all treatments to select from
                        showTreatments(treatments);
                        while (true) {
                            System.out.print("Enter Treatment ID To Book Appointment: ");
                            tid = scanner.nextLine().trim();

                            if (tid.equalsIgnoreCase("close")) {
                                // Close form
                                System.out.println("Closing the form.");
                                return false;
                            } else if (tid.isEmpty()) {
                                break;
                            } else if (!validator.isValidID(tid)) {
                                System.err.println("Invalid Treatment ID! Please enter a valid treatment ID or type 'close' to exit the form.");
                            } else {
                                treatmentID = Integer.parseInt(tid);

                                // Validate treatment ID 
                                if (!validator.isTreatmentIdExist(treatmentID, treatments)) {
                                    System.err.println("Treatment ID does not exist! Please enter a valid treatment ID or type 'close' to exit the form.");
                                } else if (!validator.isTreatmentAvailable(treatmentID, treatments)) {
                                    System.err.println("This treatment has already been booked! Please enter another treatment ID or type 'close' to exit the form.");
                                } else {
                                    break;
                                }
                            }
                        }

                        // Show all Patient list
                        pManager.showPatients();
                        while (true) {
                            System.out.print("Enter Patient ID To Book Appointment: ");
                            pid = scanner.nextLine().trim();

                            if (pid.equalsIgnoreCase("close")) {
                                // Close form
                                System.out.println("Closing the form.");
                                return false;
                            } else if (pid.isEmpty()) {
                                break;
                            } else if (!validator.isValidID(pid)) {
                                System.err.println("Invalid Patient ID! Please enter a valid patient ID or type 'close' to exit the form.");
                            } else {
                                patientID = Integer.parseInt(pid);

                                // Validate patient ID 
                                if (!validator.isExistPatientID(patientID, patients)) {
                                    System.err.println("Patient ID does not exist! Please enter a valid patient ID or type 'close' to exit the form.");
                                } else {
                                    break;
                                }
                            }
                        }

                        // Change appointment details
                        // Get appointment object
                        Appointment appointment = getAppointmentByID(appointmentID);

                        // Get old treatment ID 
                        int oldTreatmentID = appointment.getTreatmentID();
                        if (!tid.isEmpty()) {
                            appointment.setTreatmentID(treatmentID);
                            // Update old treatment status
                            getTreatmentByID(oldTreatmentID).setStatus("Available");
                            // Update new treatment status
                            getTreatmentByID(treatmentID).setStatus("Booked");
                        }

                        // Set patient ID
                        if (!pid.isBlank()) {
                            appointment.setPatientID(patientID);
                        }

                        // Set appointment booked to true
                        appointment.setStatus("Changed");
                        appointmentChanged = true;
                    }
                }
            }
        }

        return appointmentChanged;
    }

    //Cancel appointment 
    public boolean cancelAppointments() {
        boolean appointmentCanceled = false;
        String aid;
        int appointmentID;

        // Show all appointment list 
        showAppointments();
        if (!appointments.isEmpty()) {
            while (!appointmentCanceled) {
                System.out.print("Enter Appointment ID To Cancel : ");
                aid = scanner.nextLine().trim();
                if (aid.equalsIgnoreCase("close")) {
                    // Close form
                    System.out.println("Closing the form.");
                    return false;
                } else if (!validator.isValidID(aid)) {
                    System.err.println("Invalid Appointment ID! Please enter a valid appointment ID or type 'close' to exit the form.");
                } else {
                    appointmentID = Integer.parseInt(aid);

                    // Validate appointment ID 
                    if (!validator.isExistAppointmentID(appointmentID, appointments)) {
                        System.err.println("Appointment ID does not exist! Please enter a valid appointment ID or type 'close' to exit the form.");
                    } else if (validator.isAppointmentAttendOrCancel(appointmentID, appointments)) {
                        System.err.println("Appointment ID has already been attended or canceled! Please enter another appointment ID or type 'close' to exit the form.");
                    } else {
                        //Get selected appointment object
                        Appointment appointment = getAppointmentByID(appointmentID);
                        //Cancel selected appointment 
                        appointment.setStatus("Canceled");

                        //Updated selected treatment status 
                        getTreatmentByID(appointment.getTreatmentID()).setStatus("Available");

                        //Set appointmentCanceled
                        appointmentCanceled = true;

                    }
                }
            }
        }

        return appointmentCanceled;

    }

    //Attend appointment 
    public boolean attendAppointments() {
        boolean appointmentAttended = false;
        String aid;
        int appointmentID;

        // Show all appointment list 
        showAppointments();
        if (!appointments.isEmpty()) {
            while (!appointmentAttended) {
                System.out.print("Enter Appointment ID To Attend : ");
                aid = scanner.nextLine().trim();
                if (aid.equalsIgnoreCase("close")) {
                    // Close form
                    System.out.println("Closing the form.");
                    return false;
                } else if (!validator.isValidID(aid)) {
                    System.err.println("Invalid Appointment ID! Please enter a valid appointment ID or type 'close' to exit the form.");
                } else {
                    appointmentID = Integer.parseInt(aid);

                    // Validate appointment ID 
                    if (!validator.isExistAppointmentID(appointmentID, appointments)) {
                        System.err.println("Appointment ID does not exist! Please enter a valid appointment ID or type 'close' to exit the form.");
                    } else if (validator.isAppointmentAttendOrCancel(appointmentID, appointments)) {
                        System.err.println("Appointment ID has already been attended or canceled! Please enter another appointment ID or type 'close' to exit the form.");
                    } else {
                        //Get selected appointment object
                        Appointment appointment = getAppointmentByID(appointmentID);
                        //Attend selected appointment 
                        appointment.setStatus("Attended");

                        //Updated selected treatment status 
                        getTreatmentByID(appointment.getTreatmentID()).setStatus("Available");

                        //Set appointmentCanceled
                        appointmentAttended = true;

                    }
                }
            }
        }
        return appointmentAttended;
    }

    //Display all appointments
    public void showAppointments() {
        if (!appointments.isEmpty()) {
            System.out.println("\nAll Appointments Lists");
            System.out.println("_________________________________________________________________________________________________________________________________________________________________________________");
            System.out.printf("| %-14s | %-25s | %-25s | %-25s | %-15s | %-15s | %-20s | %-15s |\n", "Appointment ID", "Treatment Name", "Physiotherapist Name", "Patient Name ", "Date", "Day", "Time", "Status");
            System.out.println("_________________________________________________________________________________________________________________________________________________________________________________");
            for (Appointment appointment : appointments) {
                //Get treatement by id 
                Treatment treatment = getTreatmentByID(appointment.getTreatmentID());
                //Get physiotherapist by id 
                Physiotherapist physio = physioManager.getPhysioByID(treatment.getPhysioID());
                //Get patient by id 
                Patient patient = pManager.getPatientByID(appointment.getPatientID());

                System.out.printf("| %-14s | %-25s | %-25s | %-25s | %-15s | %-15s | %-20s | %-15s |\n",
                        "A_000" + appointment.getaID(),
                        treatment.getTreatmentName(),
                        physio.getFirstname() + " " + physio.getLastname(),
                        patient.getFirstName() + " " + patient.getLastName(),
                        treatment.getDate(),
                        treatment.getDay(),
                        treatment.getTime(),
                        appointment.getStatus());
            }
            System.out.println("_________________________________________________________________________________________________________________________________________________________________________________");
        } else {
            System.err.println("Sorry ! Appointment data not found.");
        }
    }

    //Get treatment by id 
    public Treatment getTreatmentByID(int treatmentID) {
        for (Treatment treatment : treatments) {
            if (treatment.gettID() == treatmentID) {
                return treatment;
            }
        }
        return null;
    }

    //Get appointment by id 
    public Appointment getAppointmentByID(int appointmentID) {
        for (Appointment appointment : appointments) {
            if (appointment.getaID() == appointmentID) {
                return appointment;
            }
        }
        return null;
    }

}
