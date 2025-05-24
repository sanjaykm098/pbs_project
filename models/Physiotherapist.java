package pbs_project.models;

import java.util.List;


public class Physiotherapist {
    private int id;
    private String firstname;
    private String lastname;
    private String contact;
    private String address;
    private List<String>expertise;
  
    
    //Create parameterized constructor 
    public Physiotherapist(int id, String firstname, String lastname, String contact, String address, List<String> expertise) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.contact = contact;
        this.address = address;
        this.expertise = expertise;
    }
    
    //Create getter and setter method 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getExpertise() {
        return expertise;
    }

    public void setExpertise(List<String> expertise) {
        this.expertise = expertise;
    }
    
    
}
