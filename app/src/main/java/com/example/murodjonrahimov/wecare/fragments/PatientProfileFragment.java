package com.example.murodjonrahimov.wecare.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.murodjonrahimov.wecare.PatientProfileForm;
import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.RegistrationActivity;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class PatientProfileFragment extends Fragment {


    private TextView firstName;
    private TextView lastName;
    private TextView country;
    private TextView weight;
    private TextView dob;
    private TextView gender;
    private TextView patientUserName;
    private FloatingActionButton fab;

    public PatientProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.p_fragment_profile, container, false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String userPrefferedName = preferences.getString(RegistrationActivity.USERNAME_KEY, "");

        patientUserName = rootView.findViewById(R.id.user_name);
        patientUserName.setText(userPrefferedName);

        firstName = rootView.findViewById(R.id.first_name);
        lastName = rootView.findViewById(R.id.last_name);
        country = rootView.findViewById(R.id.country);
        weight = rootView.findViewById(R.id.weight);
        dob = rootView.findViewById(R.id.dob);
        gender = rootView.findViewById(R.id.gender);
        fab = rootView.findViewById(R.id.add_fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PatientProfileForm.class);
                intent.putExtra("firstName", firstName.getText());
                intent.putExtra("lastName", lastName.getText());
                intent.putExtra("country", country.getText());
                intent.putExtra("weight", weight.getText());
                intent.putExtra("dob", dob.getText());
                intent.putExtra("gender", gender.getText());
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatabaseReference db = Database.getDatabase();

        final String userID = Database.getUserId();

        db.child("patients").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1.getKey().equals(userID)) {
                        Patient patient = dataSnapshot1.getValue(Patient.class);
                        firstName.setText(patient.getFirstName());
                        lastName.setText(patient.getLastName());
                        country.setText(patient.getCountry());
                        dob.setText(patient.getDob());
                        gender.setText(patient.getGender());
                        weight.setText(patient.getWeight());
                        patientUserName.setText(patient.getUserName());
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
