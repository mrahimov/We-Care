package com.example.murodjonrahimov.wecare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Patient;

public class PatientProfileForm extends AppCompatActivity {

  private EditText firstName;
  private EditText lastName;
  private EditText country;
  private EditText weight;
  private EditText dob;
  private EditText gender;
  private Button saveButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.patient_profile_form);

    firstName = findViewById(R.id.first_name);
    lastName = findViewById(R.id.last_name);
    country = findViewById(R.id.country);
    weight = findViewById(R.id.weight);
    dob = findViewById(R.id.dob);
    gender = findViewById(R.id.gender);
    saveButton = findViewById(R.id.save_button);

    saveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String name = firstName.getText()
          .toString();
        String surname = lastName.getText()
          .toString();
        String countryOrigin = country.getText()
          .toString();
        String patientWeight = weight.getText()
          .toString();
        String dateOfBirth = dob.getText()
          .toString();
        String sex = gender.getText()
          .toString();

        Patient patient = new Patient(name, surname, countryOrigin, dateOfBirth, patientWeight, sex);
        Database.savePatient(patient);
        finish();
      }
    });
  }
}
