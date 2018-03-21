package com.example.murodjonrahimov.wecare.model;

/**
 * Created by mohammadnaz on 3/19/18.
 */

public class DoctorPost {

    private String message;
    private String addedBy;
    private String timeStamp;
    private String key;

    public DoctorPost(String message, String addedBy, String timeStamp, String key) {
        this.message = message;
        this.addedBy = addedBy;
        this.timeStamp = timeStamp;
        this.key = key;
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
}
