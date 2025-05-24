package pbs_project.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import pbs_project.manager.PhysiotherapistManager;
import pbs_project.models.Appointment;
import pbs_project.models.Patient;
import pbs_project.models.Physiotherapist;
import pbs_project.models.Treatment;
import pbs_project.utils.Validator;

public class BookingSystem {

    private static BookingSystem instance;
    private Scanner scanner;
    private Validator validator;
    private List<Physiotherapist> physiotherapists;
    private List<Patient> patients;
    private List<Appointment> appointments;
    private List<Treatment> treatments;

    //Create default constructor 
    public BookingSystem() {
        scanner = new Scanner(System.in);
        this.validator = new Validator();
        this.physiotherapists = new ArrayList<>();
        this.patients = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.treatments = new ArrayList<>();
        addDummyPatients();
        addDummyPhysiotherapists();
        addDummyTreatments();
    }

    // Get Singleton instance
    public static BookingSystem getInstance() {
        if (instance == null) {
            instance = new BookingSystem();
        }
        return instance;
    }

    //Create getter and setter method
    public Validator getValidator() {
        return validator;
    }

    public List<Physiotherapist> getPhysiotherapists() {
        return physiotherapists;
    }

    public void setPhysiotherapists(List<Physiotherapist> physiotherapists) {
        this.physiotherapists = physiotherapists;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<Treatment> treatments) {
        this.treatments = treatments;
    }

    //Store dummy patient data 
    private void addDummyPatients() {
        patients.add(new Patient(1, "Rahul", "Sharma", "123 MG Road, Delhi", "9812345670", 30, "Male"));
        patients.add(new Patient(2, "Priya", "Verma", "456 Park Street, Mumbai", "9823456781", 25, "Female"));
        patients.add(new Patient(3, "Amit", "Patel", "789 Nehru Nagar, Ahmedabad", "9834567892", 35, "Male"));
        patients.add(new Patient(4, "Sneha", "Iyer", "101 Brigade Road, Bangalore", "9845678903", 28, "Female"));
        patients.add(new Patient(5, "Ravi", "Kumar", "202 Marina Beach, Chennai", "9856789014", 40, "Male"));
    }



    //Store dummy physiotherapist data 
    private void addDummyPhysiotherapists() {
        physiotherapists.add(new Physiotherapist(1, "Anita", "Mehta", "9900011122", "12 Lajpat Nagar, Delhi", Arrays.asList("Physiotherapy", "Osteopathy")));
        physiotherapists.add(new Physiotherapist(2, "Suresh", "Nair", "9900022233", "45 Andheri West, Mumbai", Arrays.asList("Rehabilitation", "Osteopathy")));
        physiotherapists.add(new Physiotherapist(3, "Kavita", "Desai", "9900033344", "78 CG Road, Ahmedabad", Arrays.asList("Physiotherapy")));
        physiotherapists.add(new Physiotherapist(4, "Rohan", "Reddy", "9900044455", "33 JP Nagar, Bangalore", Arrays.asList("Rehabilitation")));
        physiotherapists.add(new Physiotherapist(5, "Deepa", "Joshi", "9900055566", "90 T. Nagar, Chennai", Arrays.asList("Physiotherapy", "Rehabilitation")));
    }


    //Store dummy treatment data 
    private void addDummyTreatments() {
        treatments.add(new Treatment(1, "Back Pain Therapy", 1, "2025-06-01", "10:00 AM - 11:00 AM", "Monday", "Available"));
        treatments.add(new Treatment(2, "Joint Mobilisation", 2, "2025-06-02", "11:00 AM - 12:00 PM", "Tuesday", "Available"));
        treatments.add(new Treatment(3, "Muscle Strengthening", 3, "2025-06-03", "02:00 PM - 03:00 PM", "Wednesday", "Available"));
        treatments.add(new Treatment(4, "Postural Training", 4, "2025-06-04", "03:00 PM - 04:00 PM", "Thursday", "Available"));
        treatments.add(new Treatment(5, "Electrotherapy", 5, "2025-06-05", "09:00 AM - 10:00 AM", "Friday", "Available"));
    }


    
    //Filter treatment by physiotherapist name 
    public List<Treatment> filterTreatments(PhysiotherapistManager physioManager){
        String physioName;
        List<Treatment> filterTreatments = new ArrayList<>();
        System.out.print("Enter the physiotherapist's name to look up their treatments: ");
        physioName=scanner.nextLine().trim();
        if(physioName.equalsIgnoreCase("close")){
             // Close form
                System.out.println("Closing the form.");
                return null;
        }else if(physioName.isEmpty() || !validator.isValidName(physioName)){
             System.err.println("Invalid Physiotherapist name ! Please enter a valid name or type 'close' to exit the form.");
        }else if(!validator.isPhysioNameExist(physioName, physiotherapists)){
            System.err.println("Physiotherapist name does not exist! Please enter a valid name or type 'close' to exit the form.");
        }else {
            for(Treatment treatment : treatments){
                //Get physiotherapist object
                Physiotherapist physio = physioManager.getPhysioByID(treatment.getPhysioID());
                if(physio.getFirstname().equalsIgnoreCase(physioName) || physio.getLastname().equalsIgnoreCase(physioName)){
                    filterTreatments.add(treatment);
                }
                    
            }
            
        }
        return filterTreatments;

    }

}
