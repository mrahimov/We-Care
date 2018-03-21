package com.example.murodjonrahimov.wecare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Doctor;

public class DoctorProfileForm extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText countryOfWork;
    private EditText major;
    private EditText yearsOfExperience;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_profile_form);

        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        countryOfWork = findViewById(R.id.country);
        major = findViewById(R.id.major);
        yearsOfExperience = findViewById(R.id.years_of_experience);
        saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = firstName.getText()
                        .toString();
                String surname = lastName.getText()
                        .toString();
                String countryOfPractice = countryOfWork.getText()
                        .toString();
                String majorSpecialty = major.getText()
                        .toString();
                String yearsOfPractice = yearsOfExperience.getText()
                        .toString();

                Doctor doctor = new Doctor(name, surname, countryOfPractice, majorSpecialty, yearsOfPractice, "");
                Database.saveDoctor(doctor);
                finish();
            }
        });
    }
}
