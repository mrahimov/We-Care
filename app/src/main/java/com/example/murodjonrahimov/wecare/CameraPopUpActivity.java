package com.example.murodjonrahimov.wecare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
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

public class CameraPopUpActivity extends AppCompatActivity {

  public static final int CAMERA_REQUEST_CODE = 1;
  private ImageButton imageButtonGallery;
  private ImageButton imageButtonCamera;
  private StorageReference mStorage;
  private DatabaseReference db;
  private String userID;
  private ProgressDialog progressDialog;
  private Uri uri;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_camera_pop_up);

    db = Database.getDatabase();
    userID = Database.getUserId();
    mStorage = FirebaseStorage.getInstance().getReference();


    imageButtonCamera = findViewById(R.id.image_button_camera);
    imageButtonGallery = findViewById(R.id.image_button_gallery);

    DisplayMetrics dm = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(dm);

    progressDialog = new ProgressDialog(this);

    int width = dm.widthPixels;
    int height = dm.heightPixels;

    getWindow().setLayout((int) (width * .6), (int) (height * .3));


    imageButtonCamera.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, CAMERA_REQUEST_CODE);
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {

      progressDialog.setMessage("Uploading Image...");

       uri = data.getData();



      StorageReference docImage = mStorage.child("Photos").child(uri.getLastPathSegment());

      docImage.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

          progressDialog.dismiss();
          Toast.makeText(CameraPopUpActivity.this, "Uploading finished", Toast.LENGTH_SHORT).show();

        }
      }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          Toast.makeText(CameraPopUpActivity.this, "WeCare don't have permission to camera...", Toast.LENGTH_SHORT).show();

        }
      });
    }
  }
}
