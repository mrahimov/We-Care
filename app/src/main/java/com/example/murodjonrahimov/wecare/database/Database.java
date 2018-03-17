package com.example.murodjonrahimov.wecare.database;

import com.example.murodjonrahimov.wecare.model.Doctor;
import com.example.murodjonrahimov.wecare.model.Patient;
import com.example.murodjonrahimov.wecare.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Database {

    private static FirebaseDatabase database;

    public static FirebaseDatabase getDatabase() {

        database = FirebaseDatabase.getInstance();
        return database;
    }

    public static void savePatient(Patient patient) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        getDatabase().getReference().child("patients").child(user.getUid()).setValue(patient);
    }

    public static void savePost(Post post) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        post.setAddedBy(user.getUid());
        getDatabase().getReference().child("posts").push().setValue(post);
    }

    public static void saveDoctor(Doctor doctor) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        getDatabase().getReference().child("doctors").child(user.getUid()).setValue(doctor);

    }

    public static String getUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user.getUid();
    }

}
