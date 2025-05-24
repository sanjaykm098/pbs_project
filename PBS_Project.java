package pbs_project;

import java.util.Scanner;
import pbs_project.manager.AppointmentManager;
import pbs_project.manager.PatientManager;
import pbs_project.manager.PhysiotherapistManager;
import pbs_project.services.BookingSystem;
import pbs_project.services.ReportGenerator;

public class PBS_Project {

    public Scanner scanner;
    public BookingSystem bSystem;
    public PatientManager pManager;
    public PhysiotherapistManager physioManager;
    public AppointmentManager aManager;
    public ReportGenerator rManager;

    //Create constructor
    public PBS_Project(BookingSystem bSystem) {
        this.scanner = new Scanner(System.in);
        //initialized booking system object
        this.bSystem = bSystem;
        this.pManager = new PatientManager(bSystem);
        this.physioManager = new PhysiotherapistManager(bSystem);
        this.aManager = new AppointmentManager(bSystem, pManager, physioManager);
        this.rManager = new ReportGenerator(bSystem, aManager, physioManager);
    }

    //Start application
    public void startSystem() {
        String choice = "";
        System.out.println("================================\n\tBoost Physio Clinic\n================================");

        // Handle the user choices
        while (!choice.equals("5")) {
            System.out.println("\n\tMain Menu\n================================");
            // Show select menu
            System.out.println("""
                           1. Patient Management
                           2. Physiotherapist Management
                           3. Appointment Management
                           4. Report Management 
                           5. Close Application""");
            System.out.print("Enter Your Choice: ");
            choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                //Implement patient management functionality
                pManager.patientManagement();
            } else if (choice.equals("2")) {
                //Implement patient management functionality
                physioManager.physiotherapistManagement();
            } else if (choice.equals("3")) {
                //Implement appointment management functionality
                aManager.appointmentManagement();
            } else if (choice.equals("4")) {
                //Implement report management functionality
                rManager.ReportManagement();
            } else if (choice.equals("5")) {
                // Close Application logic here
                System.out.println("Closing application. Goodbye!");
                // Implement any cleanup or termination logic here
            } else {
                System.err.println("Sorry! Invalid choice. Please try again.");
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Initialized main class object
        BookingSystem bSystem = BookingSystem.getInstance();
        PBS_Project pbs = new PBS_Project(bSystem);
        pbs.startSystem();
    }

}