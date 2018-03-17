package com.example.murodjonrahimov.wecare.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.murodjonrahimov.wecare.DoctorProfileForm;
import com.example.murodjonrahimov.wecare.R;


public class DoctorProfileFragment extends Fragment {

    private EditText firstNameED;
    private EditText lastNameED;
    private EditText countryED;
    private EditText majorED;
    private EditText yearsOfExperienceED;
    private FloatingActionButton fab;


    public DoctorProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.d_fragment_profile, container, false);

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
}

