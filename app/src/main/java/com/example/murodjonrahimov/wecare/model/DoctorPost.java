package com.example.murodjonrahimov.wecare.model;

/**
 * Created by mohammadnaz on 3/19/18.
 */

public class DoctorPost {

  private String message;
  private String addedBy;
  private String timeStamp;
  private String key;
  private String doctorINeed;

  public DoctorPost(String message, String addedBy, String timeStamp, String doctorINeed) {
    this.message = message;
    this.addedBy = addedBy;
    this.timeStamp = timeStamp;
    this.doctorINeed = doctorINeed;
  }

  public DoctorPost(String message, String addedBy, String timeStamp, String key, String doctorINeed) {
    this.message = message;
    this.addedBy = addedBy;
    this.timeStamp = timeStamp;
    this.key = key;
    this.doctorINeed = doctorINeed;
  }

  public DoctorPost(String message, String addedBy, String timeStamp) {
    this.message = message;
    this.addedBy = addedBy;
    this.timeStamp = timeStamp;
  }

  public DoctorPost() {
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getAddedBy() {
    return addedBy;
  }

  public void setAddedBy(String addedBy) {
    this.addedBy = addedBy;
  }

  public String getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getDoctorINeed() {
    return doctorINeed;
  }

  public void setDoctorINeed(String doctorINeed) {
    this.doctorINeed = doctorINeed;
  }
}
