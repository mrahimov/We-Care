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
import com.example.murodjonrahimov.wecare.model.Patient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class PatientProfileFragment extends Fragment {


    private EditText firstName;
    private EditText lastName;
    private EditText country;
    private EditText weight;
    private EditText dob;
    private EditText gender;
    private Button saveButton;
    private Button editButton;
    private Button removeButton;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;


    public PatientProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.p_fragment_profile, container, false);

        firstName = rootView.findViewById(R.id.first_name);
        lastName = rootView.findViewById(R.id.last_name);
        country = rootView.findViewById(R.id.country);
        weight = rootView.findViewById(R.id.weight);
        dob = rootView.findViewById(R.id.dob);
        gender = rootView.findViewById(R.id.gender);
        saveButton = rootView.findViewById(R.id.save_button);
        editButton = rootView.findViewById(R.id.edit_button);
        removeButton = rootView.findViewById(R.id.remove_button);

        initFirebase();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = firstName.getText().toString();
                String surname = lastName.getText().toString();
                String countryOrigin = country.getText().toString();
                String patientWeight = weight.getText().toString();
                String dateOfBirth = dob.getText().toString();
                String sex = gender.getText().toString();

                Patient patient = new Patient(name, surname, countryOrigin, patientWeight, dateOfBirth, sex);
                Database.savePatient(patient);
            }
        });

        return rootView;
    }

    private void initFirebase() {

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();

    }

}
