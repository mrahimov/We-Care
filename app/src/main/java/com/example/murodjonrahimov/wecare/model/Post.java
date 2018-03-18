package com.example.murodjonrahimov.wecare.model;


public class Post {

    private String message;
    private String addedBy;
    private String timeStamp;

    public Post(String message, String addedBy, String timeStamp) {
        this.message = message;
        this.addedBy = addedBy;
        this.timeStamp = timeStamp;
    }

    public Post() {
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
