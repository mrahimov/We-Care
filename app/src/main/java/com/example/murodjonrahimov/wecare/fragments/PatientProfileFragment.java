package com.example.murodjonrahimov.wecare.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.model.Patient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


// fragment for PatientActivity

public class PatientProfileFragment extends Fragment {


    private EditText firstName;
    private EditText lastName;
    private EditText country;
    private EditText occupation;
    private EditText dob;
    private EditText uid;
    private Button saveButton;
    private Button editButton;
    private Button removeButton;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private Patient selectedPatient;


    public PatientProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.p_fragment_profile, container, false);

        firstName = rootView.findViewById(R.id.first_name);
        lastName = rootView.findViewById(R.id.last_name);
        country = rootView.findViewById(R.id.country);
        occupation = rootView.findViewById(R.id.occupation);
        dob = rootView.findViewById(R.id.dob);
        saveButton = rootView.findViewById(R.id.save_button);
        editButton = rootView.findViewById(R.id.edit_button);
        removeButton = rootView.findViewById(R.id.remove_button);

        initFirebase();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveNewPatientProfile();
            }
        });

//        editButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Patient patient = new Patient(selectedPatient.getUid(), selectedPatient.getLastName(), selectedPatient.getFirstName(), selectedPatient.getCountry(),
//                selectedPatient.getOccupation(), selectedPatient.getDob());
//
//                editPatientProfile(patient);
//            }
//        });
//
//        removeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//               deletePatientProfile(selectedPatient);
//            }
//        });

        return rootView;
    }

    private void initFirebase() {

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();

    }

    private void saveNewPatientProfile() {
        String name = firstName.getText().toString();
        String surname = lastName.getText().toString();
        String countryOrigin = country.getText().toString();
        String patientOccupation = occupation.getText().toString();
        String dateOfBirth = dob.getText().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Patient patient = new Patient(name, surname, countryOrigin, patientOccupation, dateOfBirth);
        mReference.child("patients").child(user.getUid()).setValue(patient);
    }

//    private void editPatientProfile(Patient selectedPatient) {
//
//        mReference.child("patients").child(selectedPatient.getUid()).child("name").setValue(selectedPatient.getFirstName());
//        mReference.child("patients").child(selectedPatient.getUid()).child("surname").setValue(selectedPatient.getLastName());
//        mReference.child("patients").child(selectedPatient.getUid()).child("countryOrigin").setValue(selectedPatient.getCountry());
//        mReference.child("patients").child(selectedPatient.getUid()).child("patientOccupation").setValue(selectedPatient.getOccupation());
//        mReference.child("patients").child(selectedPatient.getUid()).child("dateOfBirth").setValue(selectedPatient.getDob());
//    }
//
//    private void deletePatientProfile(Patient selectedPatient) {
//        mReference.child("patients").child(selectedPatient.getUid()).removeValue();
//    }

}
