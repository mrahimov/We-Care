package com.example.murodjonrahimov.wecare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Doctor;
import com.example.murodjonrahimov.wecare.model.Post;
import com.example.murodjonrahimov.wecare.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationActivity extends AppCompatActivity {

  public final static String EMAIL_KEY = "email";
  public final static String PASSWORD_KEY = "password";
  public static String USERNAME_KEY = "userKey";
  private Button registerButton;
  private CheckBox doctorCheckbox;
  private EditText licenceId;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.registration_activity);

    doctorCheckbox = findViewById(R.id.checkbox_doctor);
    licenceId = findViewById(R.id.licence_edit_text);
    registerButton = findViewById(R.id.register_account);

    final EditText emailRegistration = findViewById(R.id.email_edit_text);
    emailRegistration.setText(getIntent().getExtras()
      .getString(EMAIL_KEY));

    final EditText passwordRegistration = findViewById(R.id.password_edit_text);
    passwordRegistration.setText(getIntent().getExtras()
      .getString(PASSWORD_KEY));

    final EditText userNameRegistration = findViewById(R.id.username_edit_text);

    final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    doctorCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          licenceId.setVisibility(View.VISIBLE);
          userNameRegistration.setVisibility(View.GONE);
        }
        else {
          licenceId.setVisibility(View.INVISIBLE);
          userNameRegistration.setVisibility(View.VISIBLE);
        }
      }
    });

    registerButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String email = emailRegistration.getText()
          .toString();
        String password = passwordRegistration.getText()
          .toString();

        String username = userNameRegistration.getText().toString();

        if (email.equals("") || password.equals("") || username.equals("")) {
          Toast.makeText(RegistrationActivity.this, "Please enter a valid entry", Toast.LENGTH_LONG)
            .show();
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USERNAME_KEY, username);
        editor.apply();

        if (doctorCheckbox.isChecked() && licenceId.getText()
          .toString()
          .isEmpty()) {
          Toast.makeText(RegistrationActivity.this, "Please enter a valid licence id", Toast.LENGTH_LONG)
            .show();
        } else {

          final ProgressDialog progressDialog =
            ProgressDialog.show(RegistrationActivity.this, "Please wait ...", "Processing...", true);
          (firebaseAuth.createUserWithEmailAndPassword(email, password)).addOnCompleteListener(
            new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if (task.isSuccessful()) {
                  Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_LONG)
                    .show();

                  if (doctorCheckbox.isChecked()) {
                    Doctor doctor = new Doctor();
                    doctor.setType("doctor");
                    Database.saveDoctor(doctor);
                    finish();
                  Intent intent = new Intent(RegistrationActivity.this, DoctorActivity.class);
                  startActivity(intent);
                } if (!doctorCheckbox.isChecked()) {
                    Intent intent = new Intent(RegistrationActivity.this, PatientActivity.class);
                    startActivity(intent);
                  }
                }else {
                  Toast.makeText(RegistrationActivity.this, task.getException()
                    .getMessage(), Toast.LENGTH_LONG)
                    .show();
                }
              }
            });
        }
      }
    });
  }
}
