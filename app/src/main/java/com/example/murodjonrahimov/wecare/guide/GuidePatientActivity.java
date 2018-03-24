package com.example.murodjonrahimov.wecare.guide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.murodjonrahimov.wecare.LoginActivity;
import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.RegistrationActivity;

public class GuidePatientActivity extends AppCompatActivity {

  private Button buttonSignUp;
  private Button buttonRegister;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_guide_patient);


    buttonRegister = findViewById(R.id.guide_register);
    buttonSignUp = findViewById(R.id.guide_sign_up);


    buttonRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent intent = new Intent(GuidePatientActivity.this, RegistrationActivity.class);
        startActivity(intent);

      }
    });

    buttonSignUp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent intent = new Intent(GuidePatientActivity.this, LoginActivity.class);
        startActivity(intent);

      }
    });
  }
}

