package com.example.murodjonrahimov.wecare;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Patient;

public class PatientProfileForm extends AppCompatActivity {

  private EditText editTextFirstName;
  private EditText editTextLastName;
  private EditText editTextPatientUserName;
  private EditText editTextCountry;
  private EditText editTextWeight;
  private EditText editTextDob;
  private EditText editTextGender;
  private Button saveButton;
  private String firstName;
  private String lastName;
  private String country;
  private String weight;
  private String dob;
  private String gender;
  private String userName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.patient_profile_form);

    Bundle bundle = getIntent().getExtras();
    firstName = bundle.getString("firstName");
    lastName = bundle.getString("lastName");
    country = bundle.getString("country");
    weight = bundle.getString("weight");
    dob = bundle.getString("dob");
    gender = bundle.getString("gender");
    userName = bundle.getString("userName");

    editTextPatientUserName = findViewById(R.id.user_name);
    editTextFirstName = findViewById(R.id.first_name);
    editTextLastName = findViewById(R.id.last_name);
    editTextCountry = findViewById(R.id.country);
    editTextWeight = findViewById(R.id.weight);
    editTextDob = findViewById(R.id.dob);
    editTextGender = findViewById(R.id.gender);
    saveButton = findViewById(R.id.save_button);

    editTextPatientUserName.setText(userName);
    editTextFirstName.setText(firstName);
    editTextLastName.setText(lastName);
    editTextCountry.setText(country);
    editTextWeight.setText(weight);
    editTextDob.setText(dob);
    editTextGender.setText(gender);

    saveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String name = editTextFirstName.getText()
          .toString();
        String surname = editTextLastName.getText()
          .toString();
        String countryOrigin = editTextCountry.getText()
          .toString();
        String patientWeight = editTextWeight.getText()
          .toString();
        String dateOfBirth = editTextDob.getText()
          .toString();
        String sex = editTextGender.getText()
          .toString();
        String patientUsername = editTextPatientUserName.getText()
          .toString();

        SharedPreferences preferences = getSharedPreferences(RegistrationActivity.WE_CARE_SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(RegistrationActivity.USERNAME_KEY, patientUsername);
        editor.apply();

        Patient patient = new Patient(name, surname, countryOrigin, patientWeight, dateOfBirth, sex, patientUsername);
        Database.savePatient(patient);
        finish();
      }
    });
  }
}















