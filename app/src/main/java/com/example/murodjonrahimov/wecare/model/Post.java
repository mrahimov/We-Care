package com.example.murodjonrahimov.wecare.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {

  private String message;
  private String addedBy;
  private String timeStamp;
  private String key;
  private String postedByUserName;
  private int countOfComments;
  private String doctorINeed;

  public Post(String message, String addedBy, String timeStamp, String key, String postedByUserName, int countOfComments) {
    this.message = message;
    this.addedBy = addedBy;
    this.timeStamp = timeStamp;
    this.key = key;
    this.postedByUserName = postedByUserName;
    this.countOfComments = countOfComments;
  }

  public Post(String message, String timeStamp, String postedByUserName, String doctorINeed) {
    this.message = message;
    this.timeStamp = timeStamp;
    this.postedByUserName = postedByUserName;
    this.doctorINeed = doctorINeed;
  }

  public Post() {
  }

  public String getPostedByUserName() {
    return postedByUserName;
  }

  public void setPostedByUserName(String postedByUserName) {
    this.postedByUserName = postedByUserName;
  }

  public int getCountOfComments() {
    return countOfComments;
  }

  public void setCountOfComments(int countOfComments) {
    this.countOfComments = countOfComments;
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

  public static Creator<Post> getCREATOR() {
    return CREATOR;
  }

  public String getDoctorINeed() {
    return doctorINeed;
  }

  public void setDoctorINeed(String doctorINeed) {
    this.doctorINeed = doctorINeed;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.message);
    dest.writeString(this.addedBy);
    dest.writeString(this.timeStamp);
    dest.writeString(this.key);
    //dest.writeString(this.doctorINeed);
    dest.writeString(this.postedByUserName);
    dest.writeInt(this.countOfComments);
  }

  protected Post(Parcel in) {
    this.message = in.readString();
    this.addedBy = in.readString();
    //this.doctorINeed = in.readString();
    this.timeStamp = in.readString();
    this.key = in.readString();
    this.postedByUserName = in.readString();
    this.countOfComments = in.readInt();
  }

  public static final Creator<Post> CREATOR = new Creator<Post>() {
    @Override
    public Post createFromParcel(Parcel source) {
      return new Post(source);
    }

    @Override
    public Post[] newArray(int size) {
      return new Post[size];
    }
  };
}
