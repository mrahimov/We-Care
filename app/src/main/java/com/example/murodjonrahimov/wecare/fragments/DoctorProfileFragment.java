package com.example.murodjonrahimov.wecare.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Doctor;
import com.example.murodjonrahimov.wecare.model.Patient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DoctorProfileFragment extends Fragment {

    private EditText firstNameED;
    private EditText lastNameED;
    private EditText countryED;
    private EditText majorED;
    private EditText yearsOfExperienceED;

    private Button saveButton;
    private Button editButton;
    private Button removeButton;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;


    public DoctorProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.d_fragment_profile, container, false);

        firstNameED = rootview.findViewById(R.id.first_name);
        lastNameED = rootview.findViewById(R.id.last_name);
        countryED = rootview.findViewById(R.id.country);
        majorED = rootview.findViewById(R.id.major);
        yearsOfExperienceED = rootview.findViewById(R.id.years_of_experience);
        saveButton = rootview.findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = firstNameED.getText().toString();
                String surname = lastNameED.getText().toString();
                String country = countryED.getText().toString();
                String major = majorED.getText().toString();
                String yearsOfExp = yearsOfExperienceED.getText().toString();

                Doctor doctor = new Doctor (name, surname, country, major, yearsOfExp);
                Database.saveDoctor(doctor);
            }
        });

        return rootview;
    }

}
