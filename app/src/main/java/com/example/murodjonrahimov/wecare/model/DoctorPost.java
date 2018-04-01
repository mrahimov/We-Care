package com.example.murodjonrahimov.wecare.model;

import android.net.Uri;

/**
 * Created by mohammadnaz on 3/19/18.
 */

public class DoctorPost {

  private String message;
  private String addedBy;
  private String timeStamp;
  private String key;
  private String firstname;
  private String lastname;
  private String uri;

  public DoctorPost(String message, String addedBy, String timeStamp, String key,String firstName, String lastName) {
    this.message = message;
    this.addedBy = addedBy;
    this.timeStamp = timeStamp;
    this.key = key;
    this.firstname=firstName;
    this.lastname=lastName;
  }

  public DoctorPost(String message, String addedBy, String timeStamp, String firstname, String lastname) {
    this.message = message;
    this.addedBy = addedBy;
    this.timeStamp = timeStamp;
    this.firstname=firstname;
    this.lastname=lastname;
  }

  public DoctorPost() {
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
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
}
