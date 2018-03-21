package com.example.murodjonrahimov.wecare.model;


public class Patient {

    private String firstName;
    private String lastName;
    private String userName;
    private String country;
    private String dob;
    private String weight;
    private String gender;
    private String type;


    public Patient() {
    }

    public Patient(String firstName, String lastName, String country, String dob, String weight, String gender, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.dob = dob;
        this.weight = weight;
        this.gender = gender;
        this.userName = userName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
        return weight;
    }

    public void setOccupation(String occupation) {
        this.weight = occupation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
