package pbs_project.utils;

import java.util.List;
import pbs_project.models.Appointment;
import pbs_project.models.Patient;
import pbs_project.models.Physiotherapist;
import pbs_project.models.Treatment;

public class Validator {

    // Generic validation for pattern matching
    private boolean isValidPattern(String input, String regex) {
        return input != null && input.matches(regex);
    }

    // Validation of id
    public boolean isValidID(String id) {
        return isValidPattern(id, "\\d+");
    }

    // Validation of name exists in physiotherapists
    public boolean isPhysioNameExist(String name, List<Physiotherapist> physiotherapists) {
        return physiotherapists.stream()
                .anyMatch(physio -> physio.getFirstname().equalsIgnoreCase(name) || physio.getLastname().equalsIgnoreCase(name));
    }

    // Validation of name (for proper format)
    public boolean isValidName(String name) {
        return isValidPattern(name, "^[A-Za-z]+(?: [A-Za-z]+)*$");
    }

    // Validation of age
    public boolean isValidAge(String age) {
        try {
            int parsedAge = Integer.parseInt(age);
            return parsedAge >= 0 && parsedAge <= 100;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Validation of gender
    public boolean isValidGender(String gender) {
        return gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("M")
                || gender.equalsIgnoreCase("Female") || gender.equalsIgnoreCase("F");
    }

    // Validation of address
    public boolean isValidAddress(String address) {
        return isValidPattern(address, "^[A-Za-z0-9,.'\\s-]+$");
    }

    // Validation of contact (should be 10 digits)
    public boolean isValidContact(String contact) {
        return isValidPattern(contact, "\\d{10}");
    }

    // Check if patient ID exists
    public boolean isExistPatientID(int patientID, List<Patient> patients) {
        return patients.stream().anyMatch(patient -> patient.getpID() == patientID);
    }

    // Check if treatment ID exists
    public boolean isTreatmentIdExist(int treatmentID, List<Treatment> treatments) {
        return treatments.stream().anyMatch(treatment -> treatment.gettID() == treatmentID);
    }

    // Check if treatment is available
    public boolean isTreatmentAvailable(int treatmentID, List<Treatment> treatments) {
        return treatments.stream()
                .anyMatch(treatment -> treatment.gettID() == treatmentID && treatment.getStatus().equalsIgnoreCase("Available"));
    }

    // Check if physiotherapist ID exists
    public boolean isExistPhysiotherapistID(int physioID, List<Physiotherapist> physiotherapists) {
        return physiotherapists.stream().anyMatch(physio -> physio.getId() == physioID);
    }

    // Check if appointment ID exists
    public boolean isExistAppointmentID(int appointmentID, List<Appointment> appointments) {
        return appointments.stream().anyMatch(appointment -> appointment.getaID() == appointmentID);
    }

    // Check if appointment is attended or canceled
    public boolean isAppointmentAttendOrCancel(int appointmentID, List<Appointment> appointments) {
        return appointments.stream()
                .anyMatch(appointment -> appointment.getaID() == appointmentID
                && (appointment.getStatus().equalsIgnoreCase("Canceled") || appointment.getStatus().equalsIgnoreCase("Attended")));
    }

    // Check if patient has a booked appointment
    public boolean isPatientBookedAppointment(int patientID, List<Appointment> appointments) {
        return appointments.stream().anyMatch(appointment -> appointment.getPatientID() == patientID);
    }

    // Check if physiotherapist has a booked appointment
    public boolean isPhysioBookedAppointment(int physioID, List<Appointment> appointments) {
        return appointments.stream().anyMatch(appointment -> appointment.getPatientID() == physioID);
    }

    // Validation of expertise
    public boolean isValidExpertise(String expertise) {
        String[] expertises = {"Physiotherapy", "Osteopathy", "Rehabilitation"};
        return java.util.Arrays.stream(expertises)
                .anyMatch(value -> value.equalsIgnoreCase(expertise));
    }

    // Validation for 'close' form input
    public boolean isCloseForm(String... values) {
        return java.util.Arrays.stream(values)
                .anyMatch(value -> value.equalsIgnoreCase("close"));
    }
}
