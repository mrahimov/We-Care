package com.example.murodjonrahimov.wecare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Doctor;
import com.example.murodjonrahimov.wecare.model.Patient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

  public final static String EMAIL_KEY = "email";
  public final static String PASSWORD_KEY = "password";


  private EditText signInEmail;
  private EditText signInPassword;
  private String type;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login_activity);

    Button registerButton = findViewById(R.id.register_button);
    Button signInButton = findViewById(R.id.sign_in_button);
    signInEmail = findViewById(R.id.email_login_edit_text);
    signInPassword = findViewById(R.id.password_login_edit_text);

    final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    signInButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        type = null;

        if (signInEmail.getText()
          .toString()
          .equals("") || signInPassword.getText()
          .toString()
          .equals("")) {
          Toast.makeText(LoginActivity.this, "Please enter a valid entry", Toast.LENGTH_LONG)
            .show();
        } else {

          final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Please wait... ", "Processing...", true);

          (firebaseAuth.signInWithEmailAndPassword(signInEmail.getText()
            .toString(), signInPassword.getText()
            .toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              progressDialog.dismiss();

              if (task.isSuccessful()) {
                final String userEmail = firebaseAuth.getCurrentUser()
                  .getEmail();

                DatabaseReference db = Database.getDatabase();

                final String userID = Database.getUserId();

                db.child("doctors")
                  .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                      for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                        if (dataSnapshot2.getKey()
                          .equals(userID)) {
                          Doctor doctor = dataSnapshot2.getValue(Doctor.class);
                          type = doctor.getType();

                          if (type != null) {
                            Toast.makeText(LoginActivity.this, "Doctor Login Successful", Toast.LENGTH_LONG)
                              .show();

                            Intent intent = new Intent(LoginActivity.this, DoctorActivity.class);
                            intent.putExtra(EMAIL_KEY, firebaseAuth.getCurrentUser()
                              .getEmail());
                            startActivity(intent);
                          }
                        }
                      }
                      if (type == null) {
                        Toast.makeText(LoginActivity.this, userEmail, Toast.LENGTH_LONG)
                          .show();

                        Intent intent = new Intent(LoginActivity.this, PatientActivity.class);
                        intent.putExtra(EMAIL_KEY, firebaseAuth.getCurrentUser()
                          .getEmail());
                        startActivity(intent);
                      }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                  });
              } else {
                Toast.makeText(LoginActivity.this, task.getException()
                  .getMessage(), Toast.LENGTH_LONG)
                  .show();
              }
            }
          });
        }
      }
    });

    registerButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        intent.putExtra(EMAIL_KEY, signInEmail.getText()
          .toString());
        intent.putExtra(PASSWORD_KEY, signInPassword.getText()
          .toString());
        startActivity(intent);
      }
    });
  }
}
