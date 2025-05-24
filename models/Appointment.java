package pbs_project.models;

import java.time.LocalDateTime;


public class Appointment {
    private int aID;
    private int patientID;
    private int treatmentID;
    private LocalDateTime bookingAt;
    private String status;
    
    //Create parameterized constructor
    public Appointment(int aID, int patientID, int treatmentID, LocalDateTime bookingAt, String status) {
        this.aID = aID;
        this.patientID = patientID;
        this.treatmentID = treatmentID;
        this.bookingAt = bookingAt;
        this.status = status;
    }
    
    //Create getter and setter method

    public int getaID() {
        return aID;
    }

    public void setaID(int aID) {
        this.aID = aID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getTreatmentID() {
        return treatmentID;
    }

    public void setTreatmentID(int treatmentID) {
        this.treatmentID = treatmentID;
    }

    public LocalDateTime getBookingAt() {
        return bookingAt;
    }

    public void setBookingAt(LocalDateTime bookingAt) {
        this.bookingAt = bookingAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
