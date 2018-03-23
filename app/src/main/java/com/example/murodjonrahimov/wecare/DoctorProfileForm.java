package com.example.murodjonrahimov.wecare;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Doctor;

public class DoctorProfileForm extends AppCompatActivity {

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextCountryOfWork;
    private EditText editTextMajor;
    private EditText editTextYearsOfExperience;
    private EditText editTextUserName;
    private EditText editTextType;
    private EditText editTextPrefferedName;
    private Button saveButton;

    private String firstNameED;
    private String lastNameED;
    private String countryED;
    private String majorED;
    private String doctorPrefferedName;
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
            doctorPrefferedName = bundle.getString("doctorPrefferedName");
            yearsOfExperienceED = bundle.getString("yearsOfExperienceED");
            type = bundle.getString("type");
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String userPrefferedName = preferences.getString(RegistrationActivity.USERNAME_KEY, "");

        editTextFirstName = findViewById(R.id.first_name);
        editTextLastName = findViewById(R.id.last_name);
        editTextCountryOfWork = findViewById(R.id.country);
        editTextMajor = findViewById(R.id.major);
        editTextYearsOfExperience = findViewById(R.id.years_of_experience);
        editTextPrefferedName = findViewById(R.id.doctorPrefferedName);
        editTextType = findViewById(R.id.edit_text_type);
        saveButton = findViewById(R.id.save_button);


        editTextUserName = findViewById(R.id.user_name);
        editTextUserName.setText(userPrefferedName);

        editTextFirstName.setText(firstNameED);
        editTextLastName.setText(lastNameED);
        editTextCountryOfWork.setText(countryED);
        editTextMajor.setText(majorED);
        editTextYearsOfExperience.setText(yearsOfExperienceED);
        editTextType.setText(type);
        editTextPrefferedName.setText(doctorPrefferedName);

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

                Doctor doctor = new Doctor(name, surname, countryOfPractice, majorSpecialty, yearsOfPractice, userPrefferedName, newType);
                Database.saveDoctor(doctor);
                finish();
            }
        });
    }
}
