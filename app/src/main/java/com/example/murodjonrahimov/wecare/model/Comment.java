package com.example.murodjonrahimov.wecare.model;

public class Comment {

    private String comment;
    private String postId;
    private String timeStamp;


    public Comment(String comment, String postId, String timeStamp) {
        this.comment = comment;
        this.postId = postId;
        this.timeStamp = timeStamp;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Comment() {
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
