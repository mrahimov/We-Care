package com.example.murodjonrahimov.wecare;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Patient;
import com.example.murodjonrahimov.wecare.model.Post;
import com.squareup.picasso.Picasso;
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
  private ImageButton imageButton;
  private String doctorINeed;
  private ImageView patientPostImage01;
  private Dialog progressDialog;
  private SharedPreferences preferences;
  private String userID;
  private String uriFromBundle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.patient_post_form);

    preferences = PatientPostForm.this.getSharedPreferences(RegistrationActivity.WE_CARE_SHARED_PREFS_KEY, Context.MODE_PRIVATE);
    Bundle bundle = getIntent().getExtras();
    String downloadUriBundle = bundle.getString("downloadUri");
    String userIdBundle = bundle.getString("userId");

    if(downloadUriBundle != null && userIdBundle != null) {
      userID = userIdBundle;
      uriFromBundle = userIdBundle;

    }

    onBind();

    preferences = PatientPostForm.this.getSharedPreferences(RegistrationActivity.WE_CARE_SHARED_PREFS_KEY, Context.MODE_PRIVATE);


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
          textViewGP.setVisibility(View.VISIBLE);
        } else {

          makeTextGone();
        }
      }
    });

    imageButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(PatientPostForm.this, CameraPopUpActivity.class);
        startActivity(intent);
      }
    });

    textViewGP.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "GP";
        textviewChooseDoctor.setText(R.string.gp);
        makeTextGone();
      }
    });

    textViewAllergist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Allergist";
        textviewChooseDoctor.setText(R.string.allergist);
        makeTextGone();
      }
    });

    textViewCardiologist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Cardiologist";
        textviewChooseDoctor.setText(R.string.cardiologist);
        makeTextGone();
      }
    });

    textViewDermatologist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Dermatologist";
        textviewChooseDoctor.setText(R.string.dermatologist);
        makeTextGone();
      }
    });

    textViewNephrologist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Nephrologist";
        textviewChooseDoctor.setText(R.string.nephrologist);
        makeTextGone();
      }
    });

    textViewObstetrician.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Obstetrician/Gynecologist";
        textviewChooseDoctor.setText(R.string.obstetrician_gynecologist);
        makeTextGone();
      }
    });

    textViewOphthalmologist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Ophthalmologist";
        textviewChooseDoctor.setText(R.string.ophthalmologist);
        makeTextGone();
      }
    });

    textViewOtolaryngologist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Otolaryngologist";
        textviewChooseDoctor.setText(R.string.otolaryngologist);
        makeTextGone();
      }
    });

    textViewPediatrician.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Pediatrician";
        textviewChooseDoctor.setText(R.string.pediatrician);
        makeTextGone();
      }
    });

    textViewPsychiatrist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Psychiatrist";
        textviewChooseDoctor.setText(R.string.psychiatrist);
        makeTextGone();
      }
    });

    textViewRheumatologist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Rheumatologist";
        textviewChooseDoctor.setText(R.string.rheumatologist);
        makeTextGone();
      }
    });

    textViewUrologist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Urologist";
        textviewChooseDoctor.setText(R.string.urologist);
        makeTextGone();
      }
    });

    textViewGastroenterologist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Gastroenterologist";
        textviewChooseDoctor.setText(R.string.gastroenterologist);
        makeTextGone();
      }
    });

    textViewNeurologist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Neurologist";
        textviewChooseDoctor.setText(R.string.neurologist);
        makeTextGone();
      }
    });

    textViewOther.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doctorINeed = "Other";
        textviewChooseDoctor.setText(R.string.other);
        makeTextGone();
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

        if (doctorINeed == null) {
          doctorINeed = "Others";
        }
        Post post = new Post(message, dateString, postedByUserName, doctorINeed);
        Database.savePost(post);
        finish();
      }
    });
  }

  private void loadingProfileImage(Uri downloadUri, String Lf) {
    Log.d("url", "loadingProfileImage: " + Lf + downloadUri);
    Picasso.get()
      .load(downloadUri)
      .into(patientPostImage01);
    //progressDialog.dismiss();
  }

  public void makeTextGone() {
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
    textViewGP.setVisibility(View.GONE);
  }

  public void onBind() {
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
    imageButton = findViewById(R.id.add_image_patient_post);
    patientPostImage01 = findViewById(R.id.image_patient_post01);

  }

  //@Override
  //public View onCreateView(String name, Context context, AttributeSet attrs) {
  //  return super.onCreateView(name, context, attrs);
  //
  //  try {
  //    Uri url = Uri.parse(preferences.getString(userID, ""));
  //    if (url.toString()
  //      .length() > 0) {
  //      loadingProfileImage(url, "onResume");
  //    }
  //  } catch (IllegalArgumentException e) {
  //    e.printStackTrace();
  //  }

//  }
}


















