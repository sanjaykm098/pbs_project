package pbs_project.manager;

import java.util.List;
import java.util.Scanner;
import pbs_project.models.Appointment;
import pbs_project.models.Patient;
import pbs_project.services.BookingSystem;
import pbs_project.utils.Validator;

public class PatientManager {

    public Scanner scanner;
    public BookingSystem bSystem;
    public Validator validator;
    private List<Patient> patients;
    private List<Appointment> appointments;

    public PatientManager(BookingSystem bSystem) {
        this.scanner = new Scanner(System.in);
        this.patients = bSystem.getPatients();
        this.appointments = bSystem.getAppointments();
        this.validator = bSystem.getValidator();
        this.bSystem = bSystem;
    }

    //Patient Management 
    public void patientManagement() {
        String choice = "";
        System.out.println("\nPatient Management\n================================");
        while (!choice.equals("5")) {
            System.out.println("""
                           
                           1. Add New Patient
                           2. Remove Patient 
                           3. Update Patient Details
                           4. View All Patients
                           5. Back To Previous Menu""");
            System.out.print("Enter Your Choice : ");
            choice = scanner.nextLine().trim();
            if (choice.equals("1")) {
                boolean results = addPatients();
                if (results) {
                    System.out.println("Success ! New Patient Added Successfully.");
                }
            } else if (choice.equals("2")) {
                //get results 
                boolean results = removePatients();
                if (results) {
                    System.out.println("Success ! Patient Removed Successfully.");
                }
            } else if (choice.equals("3")) {
                //Get result
                boolean results = updatePatients();
                if (results) {
                    System.out.println("Success ! Patient Updated Successfully.");
                }
            } else if (choice.equals("4")) {
                showPatients();
            } else {

            }
        }

    }

    // Function to add new patients
    public boolean addPatients() {
        boolean patientAdded = false;
        int id = bSystem.getPatients().size() + 1;
        String firstname = "";
        String lastname = "";
        String address = "";
        String contact = "";
        String age = "";
        String gender = "";

        // Take patient details from user
        while (!patientAdded) {
            // Get Firstname
            if (firstname.equalsIgnoreCase("")) {
                System.out.print("Enter Your Firstname : ");
                firstname = scanner.nextLine().trim();

                if (firstname.isBlank() || !validator.isValidName(firstname)) {
                    System.err.println("Invalid Firstname! Please enter a valid name or type 'close' to exit the form.");
                    firstname = "";

                }
                //Get Lastname 
            } else if (lastname.equalsIgnoreCase("")) {
                System.out.print("Enter Your Lastname : ");
                lastname = scanner.nextLine().trim();

                if (lastname.isBlank() || !validator.isValidName(lastname)) {
                    System.err.println("Invalid Lastname! Please enter a valid name or type 'close' to exit the form.");
                    lastname = "";
                }

                //Get Lastname 
            } else if (contact.equalsIgnoreCase("")) {
                System.out.print("Enter Your Contact : ");
                contact = scanner.nextLine().trim();

                if (contact.isBlank() || !validator.isValidContact(contact)) {
                    System.err.println("Invalid Contact! Please enter a valid contact or type 'close' to exit the form.");
                    contact = "";
                }
                // Get Address
            } else if (address.equalsIgnoreCase("")) {
                System.out.print("Enter Your Address : ");
                address = scanner.nextLine().trim();

                if (address.isBlank() || !validator.isValidAddress(address)) {
                    System.err.println("Invalid Address ! Please enter a valid address or type 'close' to exit the form.");
                    address = "";
                }
                // Get Age
            } else if (age.equalsIgnoreCase("")) {
                System.out.print("Enter Your Age : ");
                age = scanner.nextLine().trim();

                if (age.isBlank() || !validator.isValidAge(age)) {
                    System.err.println("Invalid Age! Please enter a valid age or type 'close' to exit the form.");
                    age = "";
                }
                // Get Gender
            } else if (gender.equalsIgnoreCase("")) {
                System.out.print("Enter Your Gender : ");
                gender = scanner.nextLine().trim();

                if (gender.isBlank() || !validator.isValidGender(gender)) {
                    System.err.println("Invalid Gender! Please enter a valid gender or type 'close' to exit the form.");
                    gender = "";
                }
            } else {
                if (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("male")) {
                    gender = "Male";
                } else if (gender.equalsIgnoreCase("Female") || gender.equalsIgnoreCase("F")) {
                    gender = "Female";
                }
                patients.add(new Patient(id, firstname, lastname, address, contact, Integer.parseInt(age), gender));
                patientAdded = true;
            }

            //Close form 
            if (validator.isCloseForm(firstname, lastname, address, contact, age, gender)) {
                System.out.println("Closing the form.");
                return false;
            }

        }

        return patientAdded;
    }

    // View all patients 
    public void showPatients() {
        System.out.println("\nAll Patients Lists");
        System.out.println("_____________________________________________________________________________________________________________________________________________");
        System.out.printf("| %-10s | %-15s | %-15s | %-15s | %-15s | %-15s | %-35s |\n", "Patient ID", "FirstName", "LastName", "Contact", "Gender", "Age", "Address");
        System.out.println("_____________________________________________________________________________________________________________________________________________");
        for (Patient patient : patients) {
            System.out.printf("| %-10s | %-15s | %-15s | %-15s | %-15s | %-15s | %-35s |\n",
                    "P_000" + patient.getpID(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getContactNumber(),
                    patient.getPatientGender(),
                    patient.getPatientAge(),
                    patient.getPatientAddress());
        }
        System.out.println("_____________________________________________________________________________________________________________________________________________");

    }

    //Remove patients details
    public boolean removePatients() {
        boolean removedPatient = false;
        String pid;
        System.out.println("\nRemove Patients Form\n================================");
        //Show all patients list 
        showPatients();
        while (!removedPatient) {
            System.out.print("Enter Patient ID Remove : ");
            pid = scanner.nextLine().trim();
            if (pid.equalsIgnoreCase("close")) {
                //Close form
                System.out.println("Closing the form.");
                return false;
            } else if (!validator.isValidID(pid)) {
                System.err.println("Invalid Patient ID ! Please enter valid patient id or type or type 'close' to exit the form.");
            } else {
                int patientID = Integer.parseInt(pid);
                if (!validator.isExistPatientID(patientID, patients)) {
                    System.err.println("Patient ID does not exist ! Please enter valid patient id or type 'close' to exit the form.");
                } else if (validator.isPatientBookedAppointment(patientID, appointments)) {
                    System.err.println("This patient has an appointment booked and cannot be removed. Please enter a different patient ID, or type 'close' to exit.");
                } else {
                    //Remove patient from list 
                    patients.remove(getPatientByID(patientID));
                    removedPatient = true;
                }
            }
        }
        return removedPatient;
    }

    //Update patient details 
    public boolean updatePatients() {
        boolean patientUpdated = false;
        String pid;
        String firstname = null;
        String lastname = null;
        String contact = null;
        String address = null;
        String age = null;
        String gender = null;
        System.out.println("\nUpdate Patients Form\n================================");
        //Show all patients list 
        showPatients();
        while (!patientUpdated) {
            System.out.print("Enter Patient ID Update : ");
            pid = scanner.nextLine().trim();
            if (pid.equalsIgnoreCase("close")) {
                //Close form
                System.out.println("Closing the form.");
                return false;
            } else if (!validator.isValidID(pid)) {
                System.err.println("Invalid Patient ID ! Please enter valid patient id or type or type 'close' to exit the form.");
            } else {
                int patientID = Integer.parseInt(pid);
                if (!validator.isExistPatientID(patientID, patients)) {
                    System.err.println("Patient ID does not exist ! Please enter valid patient id or type 'close' to exit the form.");
                } else if (validator.isPatientBookedAppointment(patientID, appointments)) {
                    System.err.println("This patient has an appointment booked and cannot be removed. Please enter a different patient ID, or type 'close' to exit.");
                } else {
                    //Take patient new details from users
                    while (!patientUpdated) {
                        // Get Firstname
                        if (firstname == null) {
                            System.out.print("Enter Your Firstname : ");
                            firstname = scanner.nextLine().trim();
                            if (!firstname.isBlank()) {
                                if (!validator.isValidName(firstname)) {
                                    System.err.println("Invalid Firstname! Please enter a valid name or type 'close' to exit the form.");
                                    firstname = null;
                                }
                            }
                            //Get Lastname 
                        } else if (lastname == null) {
                            System.out.print("Enter Your Lastname : ");
                            lastname = scanner.nextLine().trim();
                            if (!lastname.isBlank()) {
                                if (!validator.isValidName(lastname)) {
                                    System.err.println("Invalid Lastname! Please enter a valid name or type 'close' to exit the form.");
                                    lastname = null;
                                }
                            }

                            //Get Lastname 
                        } else if (contact == null) {
                            System.out.print("Enter Your Contact : ");
                            contact = scanner.nextLine().trim();
                            if (!contact.isBlank()) {
                                if (!validator.isValidContact(contact)) {
                                    System.err.println("Invalid Contact! Please enter a valid contact or type 'close' to exit the form.");
                                    contact = null;
                                }
                            }
                            // Get Address
                        } else if (address == null) {
                            System.out.print("Enter Your Address : ");
                            address = scanner.nextLine().trim();
                            if (!address.isBlank()) {
                                if (!validator.isValidAddress(address)) {
                                    System.err.println("Invalid Address ! Please enter a valid address or type 'close' to exit the form.");
                                    address = null;
                                }
                            }
                            // Get Age
                        } else if (age == null) {
                            System.out.print("Enter Your Age : ");
                            age = scanner.nextLine().trim();
                            if (!age.isBlank()) {
                                if (!validator.isValidAge(age)) {
                                    System.err.println("Invalid Age! Please enter a valid age or type 'close' to exit the form.");
                                    age = null;
                                }
                            }
                            // Get Gender
                        } else if (gender == null) {
                            System.out.print("Enter Your Gender : ");
                            gender = scanner.nextLine().trim();
                            if (!gender.isBlank()) {
                                if (!validator.isValidGender(gender)) {
                                    System.err.println("Invalid Gender! Please enter a valid gender or type 'close' to exit the form.");
                                    gender = null;
                                }
                            }
                        } else {
                            //Get patient object
                            Patient patient = getPatientByID(patientID);
                            if (!firstname.isEmpty()) {
                                patient.setFirstName(firstname);
                            }
                            if (!lastname.isEmpty()) {
                                patient.setLastName(lastname);
                            }
                            if (!address.isEmpty()) {
                                patient.setPatientAddress(address);
                            }
                            if (!age.isEmpty()) {
                                patient.setPatientAge(Integer.parseInt(age));
                            }
                            if (!gender.isEmpty()) {
                                if (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("male")) {
                                    gender = "Male";
                                } else if (gender.equalsIgnoreCase("Female") || gender.equalsIgnoreCase("F")) {
                                    gender = "Female";
                                }
                                patient.setPatientGender(gender);
                            }
                            patientUpdated = true;
                        }
                    }
                }
            }
        }
        return patientUpdated;

    }

    //Get patient by id 
    public Patient getPatientByID(int id) {
        for (Patient patient : patients) {
            if (patient.getpID() == id) {
                return patient;
            }
        }
        return null;
    }

}
