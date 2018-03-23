package com.example.murodjonrahimov.wecare.model;


public class User {

    private String email;
    private String userID;
    private String userName;

    public User(String email, String userName) {
        this.email = email;
        this.userID = userID;
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
