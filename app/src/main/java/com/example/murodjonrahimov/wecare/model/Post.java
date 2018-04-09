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
  private boolean resolved;
  private String uri;

  public Post(String message, String addedBy, String timeStamp, String key, String postedByUserName, int countOfComments,
              boolean resolved) {
    this.message = message;
    this.addedBy = addedBy;
    this.timeStamp = timeStamp;
    this.key = key;
    this.postedByUserName = postedByUserName;
    this.countOfComments = countOfComments;
    this.resolved = resolved;
  }

  public Post(String message, String timeStamp, String postedByUserName, String doctorINeed, String uri) {
    this.message = message;
    this.timeStamp = timeStamp;
    this.postedByUserName = postedByUserName;
    this.doctorINeed = doctorINeed;
    this.uri = uri;
  }

  public Post(String message, String timeStamp, String postedByUserName, String doctorINeed) {
    this.message = message;
    this.timeStamp = timeStamp;
    this.postedByUserName = postedByUserName;
    this.doctorINeed = doctorINeed;
  }

  public Post() {
  }

  protected Post(Parcel in) {
    message = in.readString();
    addedBy = in.readString();
    timeStamp = in.readString();
    key = in.readString();
    postedByUserName = in.readString();
    countOfComments = in.readInt();
    doctorINeed = in.readString();
    resolved = in.readByte() != 0;
    uri = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(message);
    dest.writeString(addedBy);
    dest.writeString(timeStamp);
    dest.writeString(key);
    dest.writeString(postedByUserName);
    dest.writeInt(countOfComments);
    dest.writeString(doctorINeed);
    dest.writeByte((byte) (resolved ? 1 : 0));
    dest.writeString(uri);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<Post> CREATOR = new Creator<Post>() {
    @Override
    public Post createFromParcel(Parcel in) {
      return new Post(in);
    }

    @Override
    public Post[] newArray(int size) {
      return new Post[size];
    }
  };

  public boolean isResolved() {
    return resolved;
  }

  public void setResolved(boolean resolved) {
    this.resolved = resolved;
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

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }
}


