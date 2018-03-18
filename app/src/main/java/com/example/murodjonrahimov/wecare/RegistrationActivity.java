package com.example.murodjonrahimov.wecare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    public final static String EMAIL_KEY = "email";
    public final static String PASSWORD_KEY = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        Button registerPatient = findViewById(R.id.register_patient_button);
        Button registerDoctor = findViewById(R.id.register_doctor_button);

        final EditText emailRegistration = findViewById(R.id.email_edit_text);
        emailRegistration.setText(getIntent().getExtras().getString(EMAIL_KEY));

        final EditText passwordRegistration = findViewById(R.id.password_edit_text);
        passwordRegistration.setText(getIntent().getExtras().getString(PASSWORD_KEY));

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        registerPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailRegistration.getText().toString();
                String password = passwordRegistration.getText().toString();


                if (email.equals("") || password.equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Please enter a valid entry", Toast.LENGTH_LONG).show();
                } else {

                    final ProgressDialog progressDialog = ProgressDialog.show(RegistrationActivity.this, "Please wait ...", "Processing...", true);
                    (firebaseAuth.createUserWithEmailAndPassword(email, password)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();

                            if (task.isSuccessful()) {
                                Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(RegistrationActivity.this, PatientActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }

            }
        });

        registerDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailRegistration.getText().toString();
                String password = passwordRegistration.getText().toString();


                if (email.equals("") || password.equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Please enter a valid entry", Toast.LENGTH_LONG).show();
                } else {

                    final ProgressDialog progressDialog = ProgressDialog.show(RegistrationActivity.this, "Please wait ...", "Processing...", true);
                    (firebaseAuth.createUserWithEmailAndPassword(email, password)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();

                            if (task.isSuccessful()) {
                                Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(RegistrationActivity.this, DoctorActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }

            }
        });
    }
}



