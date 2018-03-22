package com.example.murodjonrahimov.wecare.model;

/**
 * Created by mohammadnaz on 3/21/18.
 */

public class notifyDoctorPojo {
    private String message;
    private String timestamp;

    public notifyDoctorPojo(){

    }

    public notifyDoctorPojo(String message, String timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
