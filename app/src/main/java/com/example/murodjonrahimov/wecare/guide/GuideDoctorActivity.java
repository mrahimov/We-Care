package com.example.murodjonrahimov.wecare.guide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.murodjonrahimov.wecare.LoginActivity;
import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.RegistrationActivity;

public class GuideDoctorActivity extends AppCompatActivity {

  Button buttonRegister;
  Button buttonSignUp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_guide_doctor);

    buttonRegister = findViewById(R.id.guide_doctor_register);
    buttonSignUp = findViewById(R.id.guide_doctor_sign_up);

    buttonRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent intent = new Intent(GuideDoctorActivity.this, RegistrationActivity.class);
        startActivity(intent);
      }
    });

    buttonSignUp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent intent = new Intent(GuideDoctorActivity.this, LoginActivity.class);
        startActivity(intent);
      }
    });
  }
}
