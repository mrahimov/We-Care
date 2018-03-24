package com.example.murodjonrahimov.wecare.guide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.murodjonrahimov.wecare.R;

public class GuideActivity extends AppCompatActivity {

  private Button doctorButton;
  private Button patientButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_guide);

    doctorButton = findViewById(R.id.button_guide_doctor);
    patientButton = findViewById(R.id.button_guide_patient);

    doctorButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent intent = new Intent(GuideActivity.this, GuideDoctorActivity.class);
        startActivity(intent);

      }
    });

    patientButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent intent = new Intent(GuideActivity.this, GuidePatientActivity.class);
        startActivity(intent);

      }
    });
  }
}
