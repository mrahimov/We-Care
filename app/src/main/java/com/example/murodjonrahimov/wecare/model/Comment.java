package com.example.murodjonrahimov.wecare.model;

public class Comment {

    private String comment;
    private String postId;


    public Comment(String comment, String postId) {
        this.comment = comment;
        this.postId = postId;
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
