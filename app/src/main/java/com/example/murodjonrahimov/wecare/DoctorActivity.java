package com.example.murodjonrahimov.wecare;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.fragments.DoctorsForumFragment;
import com.example.murodjonrahimov.wecare.fragments.AllPatientsPostsFragment;
import com.example.murodjonrahimov.wecare.fragments.DoctorProfileFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class DoctorActivity extends AppCompatActivity implements DoctorsForumFragment.onClickListenerDoctor {

  private ActionBar toolbar;
  DoctorsForumFragment fragment2;
  private StorageReference storageReference;
  DoctorProfileFragment doctorProfileFragment;
  AllPatientsPostsFragment allPatientsPostsFragment;
  private String key;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.doctor_activity);
    FirebaseMessaging.getInstance()
      .subscribeToTopic("notifications");

    toolbar = getSupportActionBar();
    loadFragment(new DoctorsForumFragment());

    BottomNavigationView navigation = findViewById(R.id.navigation);
    storageReference = FirebaseStorage.getInstance()
      .getReference();

    navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
          case R.id.navigation_doctors:
            toolbar.setTitle("Doctors");
            DoctorsForumFragment doctorsForumFragment =
              (DoctorsForumFragment) getSupportFragmentManager().findFragmentByTag("docFrag");
            if (doctorsForumFragment == null) {
              fragment2 = new DoctorsForumFragment();

              FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
              transaction.replace(R.id.frame_container, fragment2, "docFrag");
              transaction.addToBackStack(null);
              transaction.commit();
            } else {
              reInsertFragment(doctorsForumFragment);
            }
            return true;
          case R.id.navigation_my_profile:
            toolbar.setTitle("Doctor Profile");
            doctorProfileFragment = new DoctorProfileFragment();
            loadFragment(doctorProfileFragment);
            return true;
          case R.id.navigation_posts:
            toolbar.setTitle("Posts");
            allPatientsPostsFragment = new AllPatientsPostsFragment();
            loadFragment(allPatientsPostsFragment);
            return true;
        }
        return false;
      }
    });
  }

  private void loadFragment(android.support.v4.app.Fragment fragment) {

    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.frame_container, fragment);
    transaction.addToBackStack(null);
    transaction.commit();
  }

  public void reInsertFragment(Fragment fragment) {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.frame_container, fragment, "docFrag");
    transaction.addToBackStack(null);
    transaction.commit();
  }

  @Override
  public void onclick(String key, String message, String timestamp, String addedBy, String name, String uri) {
    Intent intent = new Intent(DoctorActivity.this, PostDoctorComments.class);
    intent.putExtra("key", key);
    intent.putExtra("message", message);
    intent.putExtra("timestamp", timestamp);
    intent.putExtra("addedby", name);
    intent.putExtra("uri", uri);
    startActivity(intent);
  }

  @Override
  public void Uri(String key) {
    this.key = key;
    Intent intent = new Intent(Intent.ACTION_PICK);
    intent.putExtra("key", key);
    intent.setType("image/*");
    startActivityForResult(intent, 7);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {

    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == 7 && resultCode == RESULT_OK) {
      Uri uri = data.getData();
      //Intent intent=getIntent();
      //final String key= intent.getStringExtra("key");

      //String uniqueID = UUID.randomUUID().toString();

      assert uri != null;
      StorageReference docImage = storageReference.child(key)
        .child(uri.getAuthority());
      docImage.putFile(uri)
        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
          @Override
          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            Uri downloadUri = taskSnapshot.getDownloadUrl();

            Database.saveDoctorURI(downloadUri.toString(), key);
          }
        });
    }
  }

  @Override
  public void onBackPressed() {
    if (fragment2.isVisible() || doctorProfileFragment.isVisible() || allPatientsPostsFragment.isVisible()) {
      //**//Do Nothing - DOES NOT CLOSE THE APP**
    } else {
      super.onBackPressed();
    }
  }
}


