package pbs_project.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import pbs_project.models.Appointment;
import pbs_project.models.Physiotherapist;
import pbs_project.services.BookingSystem;
import pbs_project.utils.Validator;

public class PhysiotherapistManager {

    public Scanner scanner;
    public BookingSystem bSystem;
    public Validator validator;
    private List<Physiotherapist> physiotherapists;
    private List<Appointment> appointments;

    //Create constructor 
    public PhysiotherapistManager(BookingSystem bSystem) {
        this.scanner = new Scanner(System.in);
        this.physiotherapists = bSystem.getPhysiotherapists();
        this.appointments = bSystem.getAppointments();
        this.validator = bSystem.getValidator();
        this.bSystem = bSystem;
    }

    //Physiotherapist Management 
    public void physiotherapistManagement() {
        String choice = "";
        System.out.println("\nPhysiotherapist Management\n================================");
        while (!choice.equals("5")) {
            System.out.println("""
                           
                           1. Add New Physiotherapist
                           2. Remove Physiotherapist 
                           3. Update Physiotherapist Details
                           4. View All Physiotherapists
                           5. Back To Previous Menu""");
            System.out.print("Enter Your Choice : ");
            choice = scanner.nextLine().trim();
            if (choice.equals("1")) {
                boolean results = addPhysiotherapists();
                if (results) {
                    System.out.println("Success ! New Patient Added Successfully.");
                }
            } else if (choice.equals("2")) {
                //get results 
                boolean results = removePhysiotherapists();
                if (results) {
                    System.out.println("Success ! Patient Removed Successfully.");
                }
            } else if (choice.equals("3")) {
                //Get result
                boolean results = updatePhysiotherapists();
                if (results) {
                    System.out.println("Success ! Patient Updated Successfully.");
                }
            } else if (choice.equals("4")) {
                viewPhysiotherapists();
            } else {

            }
        }
    }

    //Add new physiotherapist
    public boolean addPhysiotherapists() {
        boolean physioAdded = false;
        int id = physiotherapists.size() + 1;
        String firstname = "";
        String lastname = "";
        String contact = "";
        String address = "";
        String exp;
        List<String> expertise = new ArrayList<>();

        // Take physiotherapist details from user
        while (!physioAdded) {
            // Get Firstname
            if (firstname.equalsIgnoreCase("")) {
                System.out.print("Enter Your Firstname: ");
                firstname = scanner.nextLine().trim();

                if (firstname.isBlank() || !validator.isValidName(firstname)) {
                    System.err.println("Invalid Firstname! Please enter a valid name or type 'close' to exit the form.");
                    firstname = "";
                }
            } // Get Lastname 
            else if (lastname.equalsIgnoreCase("")) {
                System.out.print("Enter Your Lastname: ");
                lastname = scanner.nextLine().trim();

                if (lastname.isBlank() || !validator.isValidName(lastname)) {
                    System.err.println("Invalid Lastname! Please enter a valid name or type 'close' to exit the form.");
                    lastname = "";
                }
            } // Get Contact
            else if (contact.equalsIgnoreCase("")) {
                System.out.print("Enter Your Contact: ");
                contact = scanner.nextLine().trim();

                if (contact.isBlank() || !validator.isValidContact(contact)) {
                    System.err.println("Invalid Contact! Please enter a valid contact or type 'close' to exit the form.");
                    contact = "";
                }
            } // Get Address
            else if (address.equalsIgnoreCase("")) {
                System.out.print("Enter Your Address: ");
                address = scanner.nextLine().trim();

                if (address.isBlank() || !validator.isValidAddress(address)) {
                    System.err.println("Invalid Address! Please enter a valid address or type 'close' to exit the form.");
                    address = "";
                }
            } // Get Expertise
            else if (expertise.isEmpty()) {
                String option = "Yes"; // Start the loop with "Yes"
                while (option.equalsIgnoreCase("Yes") || option.equalsIgnoreCase("Y")) {
                    System.out.print("Enter Your Expertise (Physiotherapy/Osteopathy/Rehabilitation): ");
                    exp = scanner.nextLine().trim();

                    if (!validator.isValidExpertise(exp)) {
                        System.err.println("Invalid Expertise! Please enter a valid expertise or type 'close' to exit the form.");
                    } else {
                        expertise.add(exp);
                        System.out.print("Do you want to add more expertise (Yes/No): ");
                        option = scanner.nextLine().trim();
                    }
                }
            } // Finalize and Add Physiotherapist
            else {
                physiotherapists.add(new Physiotherapist(id, firstname, lastname, contact, address, expertise));
                physioAdded = true;
                System.out.println("Physiotherapist added successfully!");
            }

            // Close form if user enters 'close' in any field
            if (validator.isCloseForm(firstname, lastname, address, contact)) {
                System.out.println("Closing the form.");
                return false;
            }
        }

        return physioAdded;
    }

    //Remove physiotherapists
    public boolean removePhysiotherapists() {
        boolean removedPhysio = false;
        String pid;
        System.out.println("\nRemove Physiotherapists Form\n================================");

        // Show all physiotherapists list
        viewPhysiotherapists();

        while (!removedPhysio) {
            System.out.print("Enter Physiotherapist ID to Remove: ");
            pid = scanner.nextLine().trim();

            if (pid.equalsIgnoreCase("close")) {
                // Close form
                System.out.println("Closing the form.");
                return false;
            } else if (!validator.isValidID(pid)) {
                System.err.println("Invalid Physiotherapist ID! Please enter a valid ID or type 'close' to exit the form.");
            } else {
                int physioID = Integer.parseInt(pid);

                if (!validator.isExistPhysiotherapistID(physioID, physiotherapists)) {
                    System.err.println("Physiotherapist ID does not exist! Please enter a valid ID or type 'close' to exit the form.");
                } else if (validator.isPhysioBookedAppointment(physioID, appointments)) {
                    System.err.println("This physiotherapist has appointments booked and cannot be removed. Please enter a different physiotherapist ID, or type 'close' to exit.");
                } else {
                    // Remove physiotherapist from list
                    physiotherapists.remove(getPhysioByID(physioID));
                    removedPhysio = true;
                    System.out.println("Physiotherapist removed successfully.");
                }
            }
        }

        return removedPhysio;
    }

    //Upate physiotherapists
    public boolean updatePhysiotherapists() {
        boolean physioUpdated = false;
        boolean updateExpertise=true;
        String pid;
        String firstname = null;
        String lastname = null;
        String contact = null;
        String address = null;
        List<String> expertise = new ArrayList<>();

        System.out.println("\nUpdate Physiotherapists Form\n================================");
        // Show all physiotherapists list
        viewPhysiotherapists();

        while (!physioUpdated) {
            System.out.print("Enter Physiotherapist ID to Update: ");
            pid = scanner.nextLine().trim();

            if (pid.equalsIgnoreCase("close")) {
                // Close form
                System.out.println("Closing the form.");
                return false;
            } else if (!validator.isValidID(pid)) {
                System.err.println("Invalid Physiotherapist ID! Please enter a valid ID or type 'close' to exit the form.");
            } else {
                int physioID = Integer.parseInt(pid);
                if (!validator.isExistPhysiotherapistID(physioID, physiotherapists)) {
                    System.err.println("Physiotherapist ID does not exist! Please enter a valid ID or type 'close' to exit the form.");
                } else {
                    // Take physiotherapist new details from users
                    while (!physioUpdated) {
                        // Get Firstname
                        if (firstname == null) {
                            System.out.print("Enter Firstname: ");
                            firstname = scanner.nextLine().trim();
                            if (!firstname.isBlank() && !validator.isValidName(firstname)) {
                                System.err.println("Invalid Firstname! Please enter a valid name or type 'close' to exit the form.");
                                firstname = null;
                            }
                        } // Get Lastname
                        else if (lastname == null) {
                            System.out.print("Enter Lastname: ");
                            lastname = scanner.nextLine().trim();
                            if (!lastname.isBlank() && !validator.isValidName(lastname)) {
                                System.err.println("Invalid Lastname! Please enter a valid name or type 'close' to exit the form.");
                                lastname = null;
                            }
                        } // Get Contact
                        else if (contact == null) {
                            System.out.print("Enter Contact: ");
                            contact = scanner.nextLine().trim();
                            if (!contact.isBlank() && !validator.isValidContact(contact)) {
                                System.err.println("Invalid Contact! Please enter a valid contact or type 'close' to exit the form.");
                                contact = null;
                            }
                        } // Get Address
                        else if (address == null) {
                            System.out.print("Enter Address: ");
                            address = scanner.nextLine().trim();
                            if (!address.isBlank() && !validator.isValidAddress(address)) {
                                System.err.println("Invalid Address! Please enter a valid address or type 'close' to exit the form.");
                                address = null;
                            }
                        } // Get Expertise
                        else if (updateExpertise) {
                            String option = "Yes"; 
                            while (option.equalsIgnoreCase("Yes") || option.equalsIgnoreCase("Y")) {
                                System.out.print("Enter Expertise (Physiotherapy/Osteopathy/Rehabilitation): ");
                                String exp = scanner.nextLine().trim();
                                if(!exp.isEmpty()){
                                if (!validator.isValidExpertise(exp)) {
                                    System.err.println("Invalid Expertise! Please enter a valid expertise or type 'close' to exit the form.");
                                } else {
                                    expertise.add(exp);
                                    System.out.print("Do you want to add more expertise (Yes/No): ");
                                    option = scanner.nextLine().trim();
                                }
                                }else{
                                    System.out.println("empty expertise");
                                    option="no";
                                    updateExpertise=false;
                                }
                            }
                        } else {
                            // Get the physiotherapist object by ID
                            Physiotherapist physio = getPhysioByID(physioID);
                            if (physio != null) {
                                if (!firstname.isEmpty()) {
                                    physio.setFirstname(firstname);
                                }
                                if (!lastname.isEmpty()) {
                                    physio.setLastname(lastname);
                                }
                                if (!contact.isEmpty()) {
                                    physio.setContact(contact);
                                }
                                if (!address.isEmpty()) {
                                    physio.setAddress(address);
                                }
                                if (!expertise.isEmpty()) {
                                    physio.setExpertise(expertise);
                                }
                                physioUpdated = true;
                                System.out.println("Physiotherapist details updated successfully!");
                            }
                        }
                    }
                }
            }
        }

        return physioUpdated;
    }

    //View all physiotherapists
    public void viewPhysiotherapists() {
        System.out.println("\nAll Physiotherapist Lists");
        System.out.println("_____________________________________________________________________________________________________________________________________________");
        System.out.printf("| %-10s | %-15s | %-15s | %-15s | %-35s | %-33s |\n", "Physio ID", "FirstName", "LastName", "Contact", "Physiotherapist Expertise", "Address");
        System.out.println("_____________________________________________________________________________________________________________________________________________");
        for (Physiotherapist physio : physiotherapists) {
            System.out.printf("| %-10s | %-15s | %-15s | %-15s | %-35s | %-33s |\n",
                    "PH_00" + physio.getId(),
                    physio.getFirstname(),
                    physio.getLastname(),
                    physio.getContact(),
                    String.join(" | ", physio.getExpertise()),
                    physio.getAddress());
        }
        System.out.println("_____________________________________________________________________________________________________________________________________________");

    }

    //Get physiotherapist by id 
    public Physiotherapist getPhysioByID(int id) {
        for (Physiotherapist physio : physiotherapists) {
            if (physio.getId() == id) {
                return physio;
            }
        }
        return null;
    }
}
