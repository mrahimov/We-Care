package com.example.murodjonrahimov.wecare.model;

/**
 * Created by olgakoleda on 3/15/18.
 */

public class Patient {

    private String firstName;
    private String lastName;
    private String country;
    private String dob;
    private String occupation;

    public Patient(String firstName, String lastName, String country, String dob, String occupation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.dob = dob;
        this.occupation = occupation;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
