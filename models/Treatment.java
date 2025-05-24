package pbs_project.models;


public class Treatment {
    private int tID;
    private String treatmentName;
    private int physioID;
    private String Date;
    private String time;
    private String day;
    private String status;
    
    //Create parameterized constructor
    public Treatment(int tID, String treatmentName, int physioID, String Date, String time, String day, String status) {
        this.tID = tID;
        this.treatmentName = treatmentName;
        this.physioID = physioID;
        this.Date = Date;
        this.time = time;
        this.day = day;
        this.status = status;
    }
    
    //Create getter and setter method

    public int gettID() {
        return tID;
    }

    public void settID(int tID) {
        this.tID = tID;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public int getPhysioID() {
        return physioID;
    }

    public void setPhysioID(int physioID) {
        this.physioID = physioID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
