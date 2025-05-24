package pbs_project.models;


public class Patient {
    private int pID;
    private String firstName;
    private String lastName;
    private String patientAddress;
    private String contactNumber;
    private int patientAge;
    private String patientGender;
    
    //Create parameterized constructor 
    public Patient(int pID, String firstName, String lastName, String patientAddress, String contactNumber, int patientAge, String patientGender) {
        this.pID = pID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patientAddress = patientAddress;
        this.contactNumber = contactNumber;
        this.patientAge = patientAge;
        this.patientGender = patientGender;
    }
    
    //Create getter and setter method 

    public int getpID() {
        return pID;
    }

    public void setpID(int pID) {
        this.pID = pID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }
    
    
}
