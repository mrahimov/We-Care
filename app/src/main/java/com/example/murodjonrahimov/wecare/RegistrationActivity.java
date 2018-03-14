package com.example.murodjonrahimov.wecare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

// registration page for Patients & Doctors
// To Do Volha

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        Button doctorRegistrationButton = findViewById(R.id.register_doctor_button);
        Button patientRegistrationButton = findViewById(R.id.register_patient_button);

        doctorRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, DoctorActivity.class);
                startActivity(intent);
            }
        });

        patientRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, PatientActivity.class);
                startActivity(intent);
            }
        });
    }
    }
