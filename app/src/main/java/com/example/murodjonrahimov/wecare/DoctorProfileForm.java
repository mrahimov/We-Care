package com.example.murodjonrahimov.wecare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Doctor;

public class DoctorProfileForm extends AppCompatActivity {

  private EditText editTextFirstName;
  private EditText editTextLastName;
  private EditText editTextCountryOfWork;
  private EditText editTextMajor;
  private EditText editTextYearsOfExperience;
  private TextView editTextType;
  private Button saveButton;
  private String firstNameED;
  private String lastNameED;
  private String countryED;
  private String majorED;
  private String yearsOfExperienceED;
  private String type;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.doctor_profile_form);

    if (savedInstanceState == null) {
      Bundle bundle = getIntent().getExtras();
      firstNameED = bundle.getString("firstNameED");
      lastNameED = bundle.getString("lastNameED");
      countryED = bundle.getString("countryED");
      majorED = bundle.getString("majorED");
      yearsOfExperienceED = bundle.getString("yearsOfExperienceED");
      type = bundle.getString("type");
    }

    editTextFirstName = findViewById(R.id.first_name);
    editTextLastName = findViewById(R.id.last_name);
    editTextCountryOfWork = findViewById(R.id.country);
    editTextMajor = findViewById(R.id.major);
    editTextYearsOfExperience = findViewById(R.id.years_of_experience);
    editTextType = findViewById(R.id.edit_text_type);
    saveButton = findViewById(R.id.save_button);

    editTextFirstName.setText(firstNameED);
    editTextLastName.setText(lastNameED);
    editTextCountryOfWork.setText(countryED);
    editTextMajor.setText(majorED);
    editTextYearsOfExperience.setText(yearsOfExperienceED);
    editTextType.setText(type);

    saveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String name = editTextFirstName.getText()
          .toString();
        String surname = editTextLastName.getText()
          .toString();
        String countryOfPractice = editTextCountryOfWork.getText()
          .toString();
        String majorSpecialty = editTextMajor.getText()
          .toString();
        String yearsOfPractice = editTextYearsOfExperience.getText()
          .toString();
        String newType = editTextType.getText()
          .toString();

        Doctor doctor = new Doctor(name, surname, countryOfPractice, majorSpecialty, yearsOfPractice, newType);
        Database.saveDoctor(doctor);
        finish();
      }
    });
  }
}
