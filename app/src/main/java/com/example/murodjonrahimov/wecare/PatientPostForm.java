package com.example.murodjonrahimov.wecare;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Post;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.UUID;

import static com.example.murodjonrahimov.wecare.CameraPopUpActivity.CAMERA_REQUEST_CODE;

public class PatientPostForm extends AppCompatActivity {

  private static final int PATIENT_POST = 2;
  private EditText messageED;
  private Button saveButton;
  private RelativeLayout chooseDoctor;
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
  private Uri urionClick;
  private StorageReference storageReference;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.patient_post_form);
    storageReference = FirebaseStorage.getInstance()
      .getReference();

    onBind();

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

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PATIENT_POST);
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

        String uniqueID = UUID.randomUUID()
          .toString();

        final String message = messageED.getText()
          .toString();

        SharedPreferences preferences = getSharedPreferences(RegistrationActivity.WE_CARE_SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        final String postedByUserName = preferences.getString(RegistrationActivity.USERNAME_KEY, "");

        long date = System.currentTimeMillis();
        final SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy h:mm a");
        final String dateString = sdf.format(date);

        assert urionClick != null;
        if (urionClick != null) {

          StorageReference docImage = storageReference.child(uniqueID)
            .child(urionClick.getAuthority());

          final ProgressDialog progressDialog =
            ProgressDialog.show(PatientPostForm.this, "Please wait... ", "Processing...", true);

          docImage.putFile(urionClick)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
              @Override
              public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Uri downloadUri = taskSnapshot.getDownloadUrl();
                Post post = new Post(message, dateString, postedByUserName, doctorINeed, downloadUri.toString());
                Database.savePost(post);
                finish();
              }
            });
        } else {

          Post post = new Post(message, dateString, postedByUserName, doctorINeed);
          Database.savePost(post);
          finish();
        }

        if (doctorINeed == null) {
          doctorINeed = "Others";
        }
      }
    });
  }

  private void loadingProfileImage(Uri downloadUri, String Lf) {
    Picasso.get()
      .load(downloadUri)
      .into(patientPostImage01);
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

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {

    if (requestCode == PATIENT_POST && resultCode == RESULT_OK) {

      Uri uri = data.getData();
      urionClick = uri;
      loadingProfileImage(uri, "");
    } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {

      Uri uri = data.getData();
      urionClick = uri;
      loadingProfileImage(uri, "");
    }
  }
}


















