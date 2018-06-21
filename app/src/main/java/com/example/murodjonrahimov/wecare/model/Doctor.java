package com.example.murodjonrahimov.wecare.model;

public class Doctor {

  private String firstName;
  private String lastName;
  private String countryOfPractice;
  private String major;
  private String yearsOfExperience;
  private String type;
  private String Uid;
  private String uri;
  private boolean approved;

  public Doctor() {
  }

  public Doctor(String firstName, String lastName, String countryOfPractice, String major, String yearsOfExperience,
                String type, boolean approved) {

    this.firstName = firstName;
    this.lastName = lastName;
    this.countryOfPractice = countryOfPractice;
    this.major = major;
    this.yearsOfExperience = yearsOfExperience;
    this.type = type;
    this.approved=approved;

  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getUri() {
    return uri;
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

  public String getCountryOfPractice() {
    return countryOfPractice;
  }

  public void setCountryOfPractice(String countryOfPractice) {
    this.countryOfPractice = countryOfPractice;
  }

  public String getMajor() {
    return major;
  }

  public void setMajor(String major) {
    this.major = major;
  }

  public String getYearsOfExperience() {
    return yearsOfExperience;
  }

  public void setYearsOfExperience(String yearsOfExperience) {
    this.yearsOfExperience = yearsOfExperience;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setUid(String uid) {
    Uid = uid;
  }

  public String getUid() {
    return Uid;
  }

  public boolean getApproved() {
    return approved;
  }

  public void setApproved(boolean approved) {
    this.approved = approved;
  }
}
