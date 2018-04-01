package com.example.murodjonrahimov.wecare;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.murodjonrahimov.wecare.database.Database;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class CameraPopUpActivity extends AppCompatActivity {

  public static final int CAMERA_REQUEST_CODE = 1;
  private static final int PATIENT_POST = 2;
  private ImageButton imageButtonGallery;
  private ImageButton imageButtonCamera;
  private StorageReference mStorage;
  private DatabaseReference db;
  private String userID;
  private ProgressDialog progressDialog;
  private SharedPreferences.Editor myPrefsEdit;
  private SharedPreferences preferences;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_camera_pop_up);

    db = Database.getDatabase();
    userID = Database.getUserId();
    mStorage = FirebaseStorage.getInstance()
      .getReference();

    imageButtonCamera = findViewById(R.id.image_button_camera);
    imageButtonGallery = findViewById(R.id.image_button_gallery);
    preferences =
      CameraPopUpActivity.this.getSharedPreferences(RegistrationActivity.WE_CARE_SHARED_PREFS_KEY, Context.MODE_PRIVATE);

    DisplayMetrics dm = new DisplayMetrics();
    getWindowManager().getDefaultDisplay()
      .getMetrics(dm);

    progressDialog = new ProgressDialog(this);

    int width = dm.widthPixels;
    int height = dm.heightPixels;

    getWindow().setLayout((int) (width * .6), (int) (height * .3));

    //imageButtonCamera.setOnClickListener(new View.OnClickListener() {
    //
    //  @Override
    //  public void onClick(View v) {
    //
    //    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    //
    //    startActivityForResult(intent, CAMERA_REQUEST_CODE);
    //  }
    //});

    imageButtonGallery.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PATIENT_POST);
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);


    if (requestCode == PATIENT_POST && resultCode == RESULT_OK) {

      progressDialog.setMessage("Uploading image...");
      progressDialog.show();
      Uri uri = data.getData();

      assert uri != null;
      //loadingProfileImage(uri, "onActivityResult");
      StorageReference docImage = mStorage.child(userID)
        .child(uri.getAuthority());

      docImage.putFile(uri)
        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
          @Override
          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            Uri downloadUri = taskSnapshot.getDownloadUrl();
            myPrefsEdit = preferences.edit();
            myPrefsEdit.putString(userID, downloadUri.toString());
            myPrefsEdit.commit();

            Intent intent1 = new Intent(CameraPopUpActivity.this, PatientPostForm.class);
            intent1.putExtra("downloadUri", downloadUri.toString());
            intent1.putExtra("userId", userID);
          startActivity(intent1);
        }
        });
    }




    //if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
    //
    //  progressDialog.setMessage("Uploading Image...");
    //  try {
    //    uri = data.getData();
    //
    //    //StorageReference docImage = mStorage.child(userID)
    //    //  .child(uri.getQuery());
    //
    //    progressDialog.dismiss();
    //    Toast.makeText(CameraPopUpActivity.this, "Uploading finished", Toast.LENGTH_SHORT)
    //      .show();
    //
    //    Intent intent1 = new Intent(CameraPopUpActivity.this, PatientPostForm.class);
    //    intent1.putExtra("uri", uri);
    //    startActivity(intent1);
    //  } catch (Exception e) {
    //    e.printStackTrace();
    //  }
    //}

    //uri = data.getData();
    //
    //StorageReference docImage = mStorage.child(userID)
    //  .child(uri.getQuery());
    //
    //progressDialog.dismiss();
    //Toast.makeText(CameraPopUpActivity.this, "Uploading finished", Toast.LENGTH_SHORT)
    //  .show();

  }
}