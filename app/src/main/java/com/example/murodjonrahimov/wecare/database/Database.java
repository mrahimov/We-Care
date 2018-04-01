package com.example.murodjonrahimov.wecare.database;

import android.net.Uri;

import com.example.murodjonrahimov.wecare.model.Comment;
import com.example.murodjonrahimov.wecare.model.Doctor;
import com.example.murodjonrahimov.wecare.model.DoctorPost;
import com.example.murodjonrahimov.wecare.model.Patient;
import com.example.murodjonrahimov.wecare.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {

  private static FirebaseDatabase database;

  public static DatabaseReference getDatabase() {

    database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    return ref;
  }

  public static void savePatient(Patient patient) {
    FirebaseUser user = FirebaseAuth.getInstance()
      .getCurrentUser();
    getDatabase().child("patients")
      .child(user.getUid())
      .setValue(patient);
  }

  public static void savePost(Post post) {
    FirebaseUser user = FirebaseAuth.getInstance()
      .getCurrentUser();
    post.setAddedBy(user.getUid());

    getDatabase().child("posts")
      .push()
      .setValue(post);
  }

  public static void saveDoctorPost(DoctorPost doctorPost) {

    getDatabase().child("DoctorPost").push().setValue(doctorPost);
  }
  public static void saveDoctorURI(String uri, String key){
    getDatabase().child("DoctorPost").child(key).child("uri").setValue(uri);
  }
  public static void saveURIDoctor(String uri){
    getDatabase().child("doctors").child(Database.getUserId()).child("uri").setValue(uri);
  }

  public static void saveDoctor(Doctor doctor) {
    FirebaseUser user = FirebaseAuth.getInstance()
      .getCurrentUser();
    String user1= Database.getUserId();
    doctor.setUid(user1);
    getDatabase().child("doctors")
            .child(user.getUid())
      .setValue(doctor);
  }

  public static String getUserId() {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    return user.getUid();
  }

  public static void saveComment(Comment comment) {
    getDatabase().child("comments")
      .push()
      .setValue(comment);
  }

  public static void updatePost(String postID, int commentCount) {
    getDatabase().child("posts")
      .child(postID)
      .child("countOfComments")
      .setValue(commentCount);
  }
  public static void updateStatusOfPost(String postID, boolean isResolved) {
    getDatabase().child("posts")
            .child(postID)
            .child("resolved")
            .setValue(isResolved);
  }

  public static void updateDoctor(Doctor doctor) {
    getDatabase().child("doctors")
      .setValue(doctor);
  }

}
