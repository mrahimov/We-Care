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

import com.example.murodjonrahimov.wecare.RegistrationActivity;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.DoctorProfileForm;
import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.model.Doctor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DoctorProfileFragment extends Fragment {

    private TextView firstNameED;
    private TextView lastNameED;
    private TextView countryED;
    private TextView majorED;
    private TextView doctorPrefferedName;
    private TextView yearsOfExperienceED;
    private FloatingActionButton fab;

    public DoctorProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.d_fragment_profile, container, false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String userPrefferedName = preferences.getString(RegistrationActivity.USERNAME_KEY, "");

        doctorPrefferedName = rootView.findViewById(R.id.user_name);
        doctorPrefferedName.setText(userPrefferedName);

        firstNameED = rootView.findViewById(R.id.first_name);
        lastNameED = rootView.findViewById(R.id.last_name);
        countryED = rootView.findViewById(R.id.country);
        majorED = rootView.findViewById(R.id.major);
        yearsOfExperienceED = rootView.findViewById(R.id.years_of_experience);
        fab = rootView.findViewById(R.id.add_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DoctorProfileForm.class);
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

        db.child("doctors")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                            if (dataSnapshot2.getKey()
                                    .equals(userID)) {
                                Doctor doctor = dataSnapshot2.getValue(Doctor.class);
                                firstNameED.setText(doctor.getFirstName());
                                lastNameED.setText(doctor.getLastName());
                                countryED.setText(doctor.getCountryOfPractice());
                                majorED.setText(doctor.getMajor());
                                doctorPrefferedName.setText(doctor.getDoctorUserName());
                                yearsOfExperienceED.setText(doctor.getYearsOfExperience());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
