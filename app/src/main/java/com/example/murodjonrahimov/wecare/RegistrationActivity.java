package com.example.murodjonrahimov.wecare;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Doctor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;

import es.dmoral.toasty.Toasty;

import static com.example.murodjonrahimov.wecare.model.TermsAndConditions.terms;

public class RegistrationActivity extends AppCompatActivity {

  public final static String EMAIL_KEY = "email";
  public final static String PASSWORD_KEY = "password";
  public static String USERNAME_KEY = "userKey";
  public static String WE_CARE_SHARED_PREFS_KEY = "weCareSharedPrefsKey";
  private Button registerButton;
  private CheckBox doctorCheckbox;
  private EditText licenceId;
  private ViewGroup viewGroup;
  private String email;
  private String password;
  private String username;
  private String licence;
  private TextView usernameText;
  private TextView licenseTextview;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.registration_activity);
    viewGroup = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);

    doctorCheckbox = findViewById(R.id.checkbox_doctor);
    licenceId = findViewById(R.id.licence_edit_text);
    registerButton = findViewById(R.id.register_account);
    usernameText = findViewById(R.id.username_textview);
    licenseTextview = findViewById(R.id.license_textview);

    final EditText emailRegistration = findViewById(R.id.email_edit_text);
    final EditText passwordRegistration = findViewById(R.id.password_edit_text);
    final EditText userNameRegistration = findViewById(R.id.username_edit_text);
    final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    Intent intent = getIntent();

    String savedEmail = intent.getStringExtra(EMAIL_KEY);
    String savedPassword = intent.getStringExtra(PASSWORD_KEY);

    if (savedEmail != null || savedPassword != null) {
      emailRegistration.setText(savedEmail);
      passwordRegistration.setText(savedPassword);
    }
    doctorCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          licenceId.setVisibility(View.VISIBLE);
          licenseTextview.setVisibility(View.VISIBLE);
          userNameRegistration.setVisibility(View.GONE);
          usernameText.setVisibility(View.GONE);
        } else {
          licenceId.setVisibility(View.INVISIBLE);
          licenseTextview.setVisibility(View.INVISIBLE);
          userNameRegistration.setVisibility(View.VISIBLE);
          usernameText.setVisibility(View.VISIBLE);
        }
      }
    });

    registerButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        email = emailRegistration.getText()
          .toString();
        password = passwordRegistration.getText()
          .toString();
        licence = licenceId.getText()
          .toString();
        username = userNameRegistration.getText()
          .toString();

        if (email.equals("") || password.equals("") || licence.equals("") && username.equals("")) {

          Toasty.error(RegistrationActivity.this, "Please enter a valid entry", Toast.LENGTH_LONG, true)
            .show();
          return;
        }

        if (doctorCheckbox.isChecked() && licence.equals("")) {

          Toasty.error(RegistrationActivity.this, "Please enter a valid licence id", Toast.LENGTH_LONG, true)
            .show();
        }

        final ProgressDialog progressDialog =
          ProgressDialog.show(RegistrationActivity.this, "Please wait ...", "Processing...", true);

        firebaseAuth.fetchProvidersForEmail(email)
          .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
              progressDialog.dismiss();
              boolean check = !task.getResult()
                .getProviders()
                .isEmpty();

              if (check) {

                Toasty.info(RegistrationActivity.this,
                  "It looks like you already have a WeCare account for this email address. Please try login in.",
                  Toast.LENGTH_LONG, true)
                  .show();

                return;
              } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                builder.setTitle("Terms and Conditions");

                View viewInflated = LayoutInflater.from(RegistrationActivity.this)
                  .inflate(R.layout.terms_layout, viewGroup, false);
                final TextView textViewTerms = viewInflated.findViewById(R.id.terms_and_condition);
                textViewTerms.setText(terms);

                builder.setView(viewInflated);

                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    return;
                  }
                });

                builder.setPositiveButton("Accept ", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {

                    (firebaseAuth.createUserWithEmailAndPassword(email, password)).addOnCompleteListener(
                      new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> task) {
                          progressDialog.dismiss();
                          Toasty.success(RegistrationActivity.this, "Registration successful", Toast.LENGTH_LONG, true)
                            .show();

                          if (doctorCheckbox.isChecked()) {
                            Doctor doctor = new Doctor();
                            doctor.setType("doctor");
                            Database.saveDoctor(doctor);
                            Intent intent = new Intent(RegistrationActivity.this, TwoAuthActivityDoctorReg.class);
                            startActivity(intent);
                          }
                          if (!doctorCheckbox.isChecked()) {
                            Intent intent = new Intent(RegistrationActivity.this, PatientActivity.class);
                            startActivity(intent);
                          }
                          SharedPreferences preferences =
                            getSharedPreferences(RegistrationActivity.WE_CARE_SHARED_PREFS_KEY, Context.MODE_PRIVATE);
                          SharedPreferences.Editor editor = preferences.edit();
                          editor.putString(USERNAME_KEY, username);
                          editor.apply();
                        }
                      });


                    dialog.dismiss();
                  }
                });
                builder.show();
              }
            }
          });
      }
    });
  }
}