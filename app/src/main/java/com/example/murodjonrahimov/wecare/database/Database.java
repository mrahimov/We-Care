package com.example.murodjonrahimov.wecare.database;

import com.example.murodjonrahimov.wecare.model.Doctor;
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        getDatabase().child("patients").child(user.getUid()).setValue(patient);
    }

    public static void savePost(Post post) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        post.setAddedBy(user.getUid());
        getDatabase().child("posts").push().setValue(post);
    }

    public static void saveDoctor(Doctor doctor) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        getDatabase().child("doctors").child(user.getUid()).setValue(doctor);

    }

    public static String getUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user.getUid();
    }

}
