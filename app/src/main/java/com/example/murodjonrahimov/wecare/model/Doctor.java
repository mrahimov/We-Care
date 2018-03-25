package com.example.murodjonrahimov.wecare.model;

public class Doctor {

  private String firstName;
  private String lastName;
  private String countryOfPractice;
  private String major;
  private String yearsOfExperience;
  private String type;
  //  private String numberOfDoctorsComments;

  public Doctor() {
  }

  public Doctor(String firstName, String lastName, String countryOfPractice, String major, String yearsOfExperience,
                String type) {

    this.firstName = firstName;
    this.lastName = lastName;
    this.countryOfPractice = countryOfPractice;
    this.major = major;
    this.yearsOfExperience = yearsOfExperience;
    this.type = type;
    //    this.numberOfDoctorsComments = numberOfDoctorsComments;
  }

  //  public String getNumberOfDoctorsComments() {
  //    return numberOfDoctorsComments;
  //  }

  //  public void setNumberOfDoctorsComments(String numberOfDoctorsComments) {
  //    this.numberOfDoctorsComments = numberOfDoctorsComments;
  //  }

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
}
