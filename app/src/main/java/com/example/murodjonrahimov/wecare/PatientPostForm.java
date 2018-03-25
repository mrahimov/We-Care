package com.example.murodjonrahimov.wecare;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Post;
import java.text.SimpleDateFormat;

public class PatientPostForm extends AppCompatActivity {

  private EditText messageED;
  private Button saveButton;
  private LinearLayout chooseDoctor;
  private TextView textviewChooseDoctor;
  private TextView textViewGP;
  private TextView textViewAllergist;
  private TextView textViewCardiologist;
  private TextView textViewDermatologist;
  private TextView textViewNephrologist;
  private TextView textViewNeurologist;
  private TextView textViewObstetrician;
  private TextView textViewOphthalmologist;
  private TextView textViewOtolaryngologist;
  private TextView textViewPediatrician;
  private TextView textViewPsychiatrist;
  private TextView textViewRheumatologist;
  private TextView textViewUrologist;
  private TextView textViewGastroenterologist;
  private TextView textViewOther;
  private String doctorINeed;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.patient_post_form);

    saveButton = findViewById(R.id.save_button);
    messageED = findViewById(R.id.message_ed);
    chooseDoctor = findViewById(R.id.doctor_need_linearlayout);
    textViewGP = findViewById(R.id.doctor_gp);
    textViewAllergist = findViewById(R.id.doctor_allergist);
    textViewCardiologist = findViewById(R.id.doctor_cardiologist);
    textViewDermatologist = findViewById(R.id.doctor_dermatologist);
    textViewNephrologist = findViewById(R.id.doctor_nephrologist);
    textViewObstetrician = findViewById(R.id.doctor_obstetrician);
    textViewOphthalmologist = findViewById(R.id.doctor_ophthalmologist);
    textViewOtolaryngologist = findViewById(R.id.doctor_otolaryngologist);
    textViewPediatrician = findViewById(R.id.doctor_pediatrician);
    textViewPsychiatrist = findViewById(R.id.doctor_psychiatrist);
    textViewRheumatologist = findViewById(R.id.doctor_rheumatologist);
    textViewUrologist = findViewById(R.id.doctor_urologist);
    textViewGastroenterologist = findViewById(R.id.doctor_gastroenterologist);
    textViewNeurologist = findViewById(R.id.doctor_neurologist);
    textViewOther = findViewById(R.id.doctor_other);
    textviewChooseDoctor = findViewById(R.id.choose_doctor_textview);

    chooseDoctor.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        if (textViewAllergist.getVisibility() == View.GONE) {

          textViewAllergist.setVisibility(View.VISIBLE);
          textViewCardiologist.setVisibility(View.VISIBLE);
          textViewDermatologist.setVisibility(View.VISIBLE);
          textViewNephrologist.setVisibility(View.VISIBLE);
          textViewObstetrician.setVisibility(View.VISIBLE);
          textViewOphthalmologist.setVisibility(View.VISIBLE);
          textViewOtolaryngologist.setVisibility(View.VISIBLE);
          textViewPediatrician.setVisibility(View.VISIBLE);
          textViewPsychiatrist.setVisibility(View.VISIBLE);
          textViewRheumatologist.setVisibility(View.VISIBLE);
          textViewUrologist.setVisibility(View.VISIBLE);
          textViewGastroenterologist.setVisibility(View.VISIBLE);
          textViewNeurologist.setVisibility(View.VISIBLE);
          textViewOther.setVisibility(View.VISIBLE);
        } else {

          textViewAllergist.setVisibility(View.GONE);
          textViewCardiologist.setVisibility(View.GONE);
          textViewDermatologist.setVisibility(View.GONE);
          textViewNephrologist.setVisibility(View.GONE);
          textViewObstetrician.setVisibility(View.GONE);
          textViewOphthalmologist.setVisibility(View.GONE);
          textViewOtolaryngologist.setVisibility(View.GONE);
          textViewPediatrician.setVisibility(View.GONE);
          textViewPsychiatrist.setVisibility(View.GONE);
          textViewRheumatologist.setVisibility(View.GONE);
          textViewUrologist.setVisibility(View.GONE);
          textViewGastroenterologist.setVisibility(View.GONE);
          textViewNeurologist.setVisibility(View.GONE);
          textViewOther.setVisibility(View.GONE);
        }
      }
    });

    textViewGP.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "GP";
        textviewChooseDoctor.setText(R.string.gp);
        textViewAllergist.setVisibility(View.GONE);
        textViewCardiologist.setVisibility(View.GONE);
        textViewDermatologist.setVisibility(View.GONE);
        textViewNephrologist.setVisibility(View.GONE);
        textViewObstetrician.setVisibility(View.GONE);
        textViewOphthalmologist.setVisibility(View.GONE);
        textViewOtolaryngologist.setVisibility(View.GONE);
        textViewPediatrician.setVisibility(View.GONE);
        textViewPsychiatrist.setVisibility(View.GONE);
        textViewRheumatologist.setVisibility(View.GONE);
        textViewUrologist.setVisibility(View.GONE);
        textViewGastroenterologist.setVisibility(View.GONE);
        textViewNeurologist.setVisibility(View.GONE);
        textViewOther.setVisibility(View.GONE);
      }
    });

    textViewAllergist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Allergist";
        textviewChooseDoctor.setText(R.string.allergist);
        textViewAllergist.setVisibility(View.GONE);
        textViewCardiologist.setVisibility(View.GONE);
        textViewDermatologist.setVisibility(View.GONE);
        textViewNephrologist.setVisibility(View.GONE);
        textViewObstetrician.setVisibility(View.GONE);
        textViewOphthalmologist.setVisibility(View.GONE);
        textViewOtolaryngologist.setVisibility(View.GONE);
        textViewPediatrician.setVisibility(View.GONE);
        textViewPsychiatrist.setVisibility(View.GONE);
        textViewRheumatologist.setVisibility(View.GONE);
        textViewUrologist.setVisibility(View.GONE);
        textViewGastroenterologist.setVisibility(View.GONE);
        textViewNeurologist.setVisibility(View.GONE);
        textViewOther.setVisibility(View.GONE);
      }
    });

    textViewCardiologist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Cardiologist";
        textviewChooseDoctor.setText(R.string.cardiologist);
        textViewAllergist.setVisibility(View.GONE);
        textViewCardiologist.setVisibility(View.GONE);
        textViewDermatologist.setVisibility(View.GONE);
        textViewNephrologist.setVisibility(View.GONE);
        textViewObstetrician.setVisibility(View.GONE);
        textViewOphthalmologist.setVisibility(View.GONE);
        textViewOtolaryngologist.setVisibility(View.GONE);
        textViewPediatrician.setVisibility(View.GONE);
        textViewPsychiatrist.setVisibility(View.GONE);
        textViewRheumatologist.setVisibility(View.GONE);
        textViewUrologist.setVisibility(View.GONE);
        textViewGastroenterologist.setVisibility(View.GONE);
        textViewNeurologist.setVisibility(View.GONE);
        textViewOther.setVisibility(View.GONE);
      }
    });

    textViewDermatologist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Dermatologist";
        textviewChooseDoctor.setText(R.string.dermatologist);
        textViewAllergist.setVisibility(View.GONE);
        textViewCardiologist.setVisibility(View.GONE);
        textViewDermatologist.setVisibility(View.GONE);
        textViewNephrologist.setVisibility(View.GONE);
        textViewObstetrician.setVisibility(View.GONE);
        textViewOphthalmologist.setVisibility(View.GONE);
        textViewOtolaryngologist.setVisibility(View.GONE);
        textViewPediatrician.setVisibility(View.GONE);
        textViewPsychiatrist.setVisibility(View.GONE);
        textViewRheumatologist.setVisibility(View.GONE);
        textViewUrologist.setVisibility(View.GONE);
        textViewGastroenterologist.setVisibility(View.GONE);
        textViewNeurologist.setVisibility(View.GONE);
        textViewOther.setVisibility(View.GONE);
      }
    });

    textViewNephrologist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Nephrologist";
        textviewChooseDoctor.setText(R.string.nephrologist);
        textViewAllergist.setVisibility(View.GONE);
        textViewCardiologist.setVisibility(View.GONE);
        textViewDermatologist.setVisibility(View.GONE);
        textViewNephrologist.setVisibility(View.GONE);
        textViewObstetrician.setVisibility(View.GONE);
        textViewOphthalmologist.setVisibility(View.GONE);
        textViewOtolaryngologist.setVisibility(View.GONE);
        textViewPediatrician.setVisibility(View.GONE);
        textViewPsychiatrist.setVisibility(View.GONE);
        textViewRheumatologist.setVisibility(View.GONE);
        textViewUrologist.setVisibility(View.GONE);
        textViewGastroenterologist.setVisibility(View.GONE);
        textViewNeurologist.setVisibility(View.GONE);
        textViewOther.setVisibility(View.GONE);
      }
    });

    textViewObstetrician.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Obstetrician/Gynecologist";
        textviewChooseDoctor.setText(R.string.obstetrician_gynecologist);
        textViewAllergist.setVisibility(View.GONE);
        textViewCardiologist.setVisibility(View.GONE);
        textViewDermatologist.setVisibility(View.GONE);
        textViewNephrologist.setVisibility(View.GONE);
        textViewObstetrician.setVisibility(View.GONE);
        textViewOphthalmologist.setVisibility(View.GONE);
        textViewOtolaryngologist.setVisibility(View.GONE);
        textViewPediatrician.setVisibility(View.GONE);
        textViewPsychiatrist.setVisibility(View.GONE);
        textViewRheumatologist.setVisibility(View.GONE);
        textViewUrologist.setVisibility(View.GONE);
        textViewGastroenterologist.setVisibility(View.GONE);
        textViewNeurologist.setVisibility(View.GONE);
        textViewOther.setVisibility(View.GONE);
      }
    });

    textViewOphthalmologist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Ophthalmologist";
        textviewChooseDoctor.setText(R.string.ophthalmologist);
        textViewAllergist.setVisibility(View.GONE);
        textViewCardiologist.setVisibility(View.GONE);
        textViewDermatologist.setVisibility(View.GONE);
        textViewNephrologist.setVisibility(View.GONE);
        textViewObstetrician.setVisibility(View.GONE);
        textViewOphthalmologist.setVisibility(View.GONE);
        textViewOtolaryngologist.setVisibility(View.GONE);
        textViewPediatrician.setVisibility(View.GONE);
        textViewPsychiatrist.setVisibility(View.GONE);
        textViewRheumatologist.setVisibility(View.GONE);
        textViewUrologist.setVisibility(View.GONE);
        textViewGastroenterologist.setVisibility(View.GONE);
        textViewNeurologist.setVisibility(View.GONE);
        textViewOther.setVisibility(View.GONE);
      }
    });

    textViewOtolaryngologist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Otolaryngologist";
        textviewChooseDoctor.setText(R.string.otolaryngologist);
        textViewAllergist.setVisibility(View.GONE);
        textViewCardiologist.setVisibility(View.GONE);
        textViewDermatologist.setVisibility(View.GONE);
        textViewNephrologist.setVisibility(View.GONE);
        textViewObstetrician.setVisibility(View.GONE);
        textViewOphthalmologist.setVisibility(View.GONE);
        textViewOtolaryngologist.setVisibility(View.GONE);
        textViewPediatrician.setVisibility(View.GONE);
        textViewPsychiatrist.setVisibility(View.GONE);
        textViewRheumatologist.setVisibility(View.GONE);
        textViewUrologist.setVisibility(View.GONE);
        textViewGastroenterologist.setVisibility(View.GONE);
        textViewNeurologist.setVisibility(View.GONE);
        textViewOther.setVisibility(View.GONE);
      }
    });

    textViewPediatrician.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Pediatrician";
        textviewChooseDoctor.setText(R.string.pediatrician);
        textViewAllergist.setVisibility(View.GONE);
        textViewCardiologist.setVisibility(View.GONE);
        textViewDermatologist.setVisibility(View.GONE);
        textViewNephrologist.setVisibility(View.GONE);
        textViewObstetrician.setVisibility(View.GONE);
        textViewOphthalmologist.setVisibility(View.GONE);
        textViewOtolaryngologist.setVisibility(View.GONE);
        textViewPediatrician.setVisibility(View.GONE);
        textViewPsychiatrist.setVisibility(View.GONE);
        textViewRheumatologist.setVisibility(View.GONE);
        textViewUrologist.setVisibility(View.GONE);
        textViewGastroenterologist.setVisibility(View.GONE);
        textViewNeurologist.setVisibility(View.GONE);
        textViewOther.setVisibility(View.GONE);
      }
    });

    textViewPsychiatrist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Psychiatrist";
        textviewChooseDoctor.setText(R.string.psychiatrist);
        textViewAllergist.setVisibility(View.GONE);
        textViewCardiologist.setVisibility(View.GONE);
        textViewDermatologist.setVisibility(View.GONE);
        textViewNephrologist.setVisibility(View.GONE);
        textViewObstetrician.setVisibility(View.GONE);
        textViewOphthalmologist.setVisibility(View.GONE);
        textViewOtolaryngologist.setVisibility(View.GONE);
        textViewPediatrician.setVisibility(View.GONE);
        textViewPsychiatrist.setVisibility(View.GONE);
        textViewRheumatologist.setVisibility(View.GONE);
        textViewUrologist.setVisibility(View.GONE);
        textViewGastroenterologist.setVisibility(View.GONE);
        textViewNeurologist.setVisibility(View.GONE);
        textViewOther.setVisibility(View.GONE);
      }
    });

    textViewRheumatologist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Rheumatologist";
        textviewChooseDoctor.setText(R.string.rheumatologist);
        textViewAllergist.setVisibility(View.GONE);
        textViewCardiologist.setVisibility(View.GONE);
        textViewDermatologist.setVisibility(View.GONE);
        textViewNephrologist.setVisibility(View.GONE);
        textViewObstetrician.setVisibility(View.GONE);
        textViewOphthalmologist.setVisibility(View.GONE);
        textViewOtolaryngologist.setVisibility(View.GONE);
        textViewPediatrician.setVisibility(View.GONE);
        textViewPsychiatrist.setVisibility(View.GONE);
        textViewRheumatologist.setVisibility(View.GONE);
        textViewUrologist.setVisibility(View.GONE);
        textViewGastroenterologist.setVisibility(View.GONE);
        textViewNeurologist.setVisibility(View.GONE);
        textViewOther.setVisibility(View.GONE);
      }
    });

    textViewUrologist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Urologist";
        textviewChooseDoctor.setText(R.string.urologist);
        textViewAllergist.setVisibility(View.GONE);
        textViewCardiologist.setVisibility(View.GONE);
        textViewDermatologist.setVisibility(View.GONE);
        textViewNephrologist.setVisibility(View.GONE);
        textViewObstetrician.setVisibility(View.GONE);
        textViewOphthalmologist.setVisibility(View.GONE);
        textViewOtolaryngologist.setVisibility(View.GONE);
        textViewPediatrician.setVisibility(View.GONE);
        textViewPsychiatrist.setVisibility(View.GONE);
        textViewRheumatologist.setVisibility(View.GONE);
        textViewUrologist.setVisibility(View.GONE);
        textViewGastroenterologist.setVisibility(View.GONE);
        textViewNeurologist.setVisibility(View.GONE);
        textViewOther.setVisibility(View.GONE);
      }
    });

    textViewGastroenterologist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Gastroenterologist";
        textviewChooseDoctor.setText(R.string.gastroenterologist);
        textViewAllergist.setVisibility(View.GONE);
        textViewCardiologist.setVisibility(View.GONE);
        textViewDermatologist.setVisibility(View.GONE);
        textViewNephrologist.setVisibility(View.GONE);
        textViewObstetrician.setVisibility(View.GONE);
        textViewOphthalmologist.setVisibility(View.GONE);
        textViewOtolaryngologist.setVisibility(View.GONE);
        textViewPediatrician.setVisibility(View.GONE);
        textViewPsychiatrist.setVisibility(View.GONE);
        textViewRheumatologist.setVisibility(View.GONE);
        textViewUrologist.setVisibility(View.GONE);
        textViewGastroenterologist.setVisibility(View.GONE);
        textViewNeurologist.setVisibility(View.GONE);
        textViewOther.setVisibility(View.GONE);
      }
    });

    textViewNeurologist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Neurologist";
        textviewChooseDoctor.setText(R.string.neurologist);
        textViewAllergist.setVisibility(View.GONE);
        textViewCardiologist.setVisibility(View.GONE);
        textViewDermatologist.setVisibility(View.GONE);
        textViewNephrologist.setVisibility(View.GONE);
        textViewObstetrician.setVisibility(View.GONE);
        textViewOphthalmologist.setVisibility(View.GONE);
        textViewOtolaryngologist.setVisibility(View.GONE);
        textViewPediatrician.setVisibility(View.GONE);
        textViewPsychiatrist.setVisibility(View.GONE);
        textViewRheumatologist.setVisibility(View.GONE);
        textViewUrologist.setVisibility(View.GONE);
        textViewGastroenterologist.setVisibility(View.GONE);
        textViewNeurologist.setVisibility(View.GONE);
        textViewOther.setVisibility(View.GONE);
      }
    });

    textViewOther.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Other";
        textviewChooseDoctor.setText(R.string.other);
        textViewAllergist.setVisibility(View.GONE);
        textViewCardiologist.setVisibility(View.GONE);
        textViewDermatologist.setVisibility(View.GONE);
        textViewNephrologist.setVisibility(View.GONE);
        textViewObstetrician.setVisibility(View.GONE);
        textViewOphthalmologist.setVisibility(View.GONE);
        textViewOtolaryngologist.setVisibility(View.GONE);
        textViewPediatrician.setVisibility(View.GONE);
        textViewPsychiatrist.setVisibility(View.GONE);
        textViewRheumatologist.setVisibility(View.GONE);
        textViewUrologist.setVisibility(View.GONE);
        textViewGastroenterologist.setVisibility(View.GONE);
        textViewNeurologist.setVisibility(View.GONE);
        textViewOther.setVisibility(View.GONE);
      }
    });

    saveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String message = messageED.getText()
          .toString();

        SharedPreferences preferences = getSharedPreferences(RegistrationActivity.WE_CARE_SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        final String postedByUserName = preferences.getString(RegistrationActivity.USERNAME_KEY, "");

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy h:mm a");
        String dateString = sdf.format(date);

        Post post = new Post(message, dateString, postedByUserName, doctorINeed);
        Database.savePost(post);
        finish();
      }
    });
  }
}
