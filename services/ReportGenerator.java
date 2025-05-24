package pbs_project.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import pbs_project.manager.AppointmentManager;
import pbs_project.manager.PhysiotherapistManager;
import pbs_project.models.Appointment;
import pbs_project.models.Physiotherapist;

public class ReportGenerator {

    public Scanner scanner;
    private BookingSystem bSystem;
    private AppointmentManager aManager;
    private PhysiotherapistManager physioManager;

    //Create constructor 
    public ReportGenerator(BookingSystem bSystem, AppointmentManager aManager, PhysiotherapistManager physioManager) {
        this.scanner = new Scanner(System.in);
        this.bSystem = bSystem;
        this.aManager = aManager;
        this.physioManager = physioManager;
    }

    //Report Management 
    public void ReportManagement() {
        String choice = "";
        System.out.println("\nReport Management\n================================");
        while (!choice.equals("3")) {
            System.out.println("""
                           
                           1. Physiotherapists All Appointments
                           2. Physiotherapist Attend Appointment Report 
                           3. Back To Previous Menu""");
            System.out.print("Enter Your Choice : ");
            choice = scanner.nextLine().trim();
            if (choice.equals("1")) {
                aManager.showAppointments();
            } else if (choice.equals("2")) {
                showAttendAppointmentReport();
            } else {
                System.out.println("Return to the previous menu.");
            }
        }
    }

    //Show report of total appointment attended by physiotherapsits
    public void showAttendAppointmentReport() {
        System.out.println("\nPhysiotherapist Attended Appointment Reports");
        System.out.println("___________________________________________________________________________");
        System.out.printf("|%-10s | %-30s | %-27s |\n", "S.N", "Physiotherapist Name", "Total Attend Appointment");
        System.out.println("___________________________________________________________________________");

        // Create a list of physiotherapists and their total appointments
        List<Physiotherapist> physioList = new ArrayList<>(bSystem.getPhysiotherapists());

        // Sort the list by total attended appointments in descending order
        physioList.sort((physio1, physio2)
                -> Integer.compare(calculateAttendAppointments(physio2.getId()), calculateAttendAppointments(physio1.getId()))
        );

        
        int sn = 1;
        for (Physiotherapist physio : physioList) {
            int totalAppointments = calculateAttendAppointments(physio.getId());
            System.out.printf("|%-10d | %-30s | %-27d |\n", sn++, physio.getFirstname() + " " + physio.getLastname(), totalAppointments);
        }
        System.out.println("___________________________________________________________________________");
    }

    //Calculate the attended appointment by physiotherapist
    public int calculateAttendAppointments(int physioID) {
        int totalAttends = 0;
        for (Appointment appointment : bSystem.getAppointments()) {
            //Get Physiotherapist object 
            Physiotherapist physio = physioManager.getPhysioByID(aManager.getTreatmentByID(appointment.getTreatmentID()).getPhysioID());
            if (physio.getId() == physioID) {
                if (appointment.getStatus().equalsIgnoreCase("Attended")) {
                    totalAttends++;
                }
            }
        }

        return totalAttends;
    }

}
