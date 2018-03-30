package com.example.murodjonrahimov.wecare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Doctor;

public class TwoAuthActivityDoctorReg extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_auth_doctor_reg);
        firstName = findViewById(R.id.first_name1);
        lastName = findViewById(R.id.last_name1);
        button = findViewById(R.id.savefirstlastname);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastName.getText().toString().matches("") && firstName.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(),"information invalid",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    Doctor doctor = new Doctor();
                    doctor.setFirstName(firstName.getText().toString());
                    doctor.setLastName(lastName.getText().toString());
                    doctor.setType("doctor");
                    Database.saveDoctor(doctor);
                    Intent intent = new Intent(
                            TwoAuthActivityDoctorReg.this,
                            DoctorActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
