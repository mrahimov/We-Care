package com.example.murodjonrahimov.wecare.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.murodjonrahimov.wecare.LoginActivity;
import com.example.murodjonrahimov.wecare.RegistrationActivity;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.DoctorProfileForm;
import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.model.Doctor;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class DoctorProfileFragment extends Fragment {

  private static final int DOCTOR_IMAGE = 7;

  private String userID;
  private TextView firstNameED;
  private TextView lastNameED;
  private TextView countryED;
  private TextView majorED;
  private TextView yearsOfExperienceED;
  private TextView type;
  private TextView numberOfDoctorsComments;
  private FloatingActionButton fab;
  private ImageView doctorImage;
  private StorageReference storageReference;
  private ProgressDialog progressDialog;
  private Button buttonDoctorImage;
  private Uri uri;
  private SharedPreferences preferences;
  private SharedPreferences.Editor myPrefsEdit;

  public DoctorProfileFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.d_fragment_profile, container, false);
    setHasOptionsMenu(true);

    preferences = getActivity().getSharedPreferences(RegistrationActivity.WE_CARE_SHARED_PREFS_KEY, Context.MODE_PRIVATE);

    firstNameED = rootView.findViewById(R.id.first_name);
    lastNameED = rootView.findViewById(R.id.last_name);
    countryED = rootView.findViewById(R.id.country);
    majorED = rootView.findViewById(R.id.major);
    yearsOfExperienceED = rootView.findViewById(R.id.years_of_experience);
    type = rootView.findViewById(R.id.type_doctor);
    numberOfDoctorsComments = rootView.findViewById(R.id.number_of_doctors_comments);
    buttonDoctorImage = rootView.findViewById(R.id.button_doctor_image);
    fab = rootView.findViewById(R.id.add_fab);
    doctorImage = rootView.findViewById(R.id.imageview_doctor_profile);
    progressDialog = new ProgressDialog(getContext());

    storageReference = FirebaseStorage.getInstance()
      .getReference();

    buttonDoctorImage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, DOCTOR_IMAGE);
      }
    });

    fab.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getActivity(), DoctorProfileForm.class);
        intent.putExtra("firstNameED", firstNameED.getText());
        intent.putExtra("lastNameED", lastNameED.getText());
        intent.putExtra("countryED", countryED.getText());
        intent.putExtra("majorED", majorED.getText());
        intent.putExtra("yearsOfExperienceED", yearsOfExperienceED.getText());
        intent.putExtra("type", type.getText());
        startActivity(intent);
      }
    });

    return rootView;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {

    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == DOCTOR_IMAGE && resultCode == RESULT_OK) {

      progressDialog.setMessage("Uploading image...");
      progressDialog.show();
      uri = data.getData();

      assert uri != null;
      //myPrefsEdit = preferences.edit();
      //myPrefsEdit.putString(userID, uri.toString());
      //myPrefsEdit.commit();
      loadingProfileImage(uri, "onActivityResult");
      //Log.d("userID= ", "onActivityResult: " + userID);
      //Uri url = Uri.parse(preferences.getString(userID, ""));
      //if (url != null) {
      //  loadingProfileImage(url);
      //} else {
      //  loadingProfileImage(uri);
      //}
      StorageReference docImage = storageReference.child(userID)
        .child(uri.getAuthority());

      docImage.putFile(uri)
        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
          @Override
          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            Uri downloadUri = taskSnapshot.getDownloadUrl();
            myPrefsEdit = preferences.edit();
            myPrefsEdit.putString(userID, downloadUri.toString());
            myPrefsEdit.commit();
          }
        });
    }
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    DatabaseReference db = Database.getDatabase();
    userID = Database.getUserId();

    db.child("doctors")
      .addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
            if (dataSnapshot2.getKey()
              .equals(userID)) {
              Doctor doctor = dataSnapshot2.getValue(Doctor.class);
              firstNameED.setText(doctor.getFirstName());
              lastNameED.setText(doctor.getLastName());
              countryED.setText(doctor.getCountryOfPractice());
              majorED.setText(doctor.getMajor());
              yearsOfExperienceED.setText(doctor.getYearsOfExperience());
              type.setText(doctor.getType());
            }
          }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
      });
    Log.d("userID= ", "onViewCreated: " + userID);

    try {
      Uri url = Uri.parse(preferences.getString(userID, ""));
      Log.d("url", "onViewCreated:onViewCreated " + url.toString());

      if (url.toString()
        .length() > 0) {
        loadingProfileImage(url, "onViewCreated");
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
    if (uri != null) {
      loadingProfileImage(uri, "onViewCreated");
    }
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.settings_menu, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()) {
      case R.id.log_out:
        FirebaseAuth.getInstance()
          .signOut();
        getActivity().finishAffinity();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        return true;
      case R.id.language:
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onResume() {
    super.onResume();

    try {
      Uri url = Uri.parse(preferences.getString(userID, ""));
      if (url.toString()
        .length() > 0) {
        loadingProfileImage(url, "onResume");
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
  }

  private void loadingProfileImage(Uri downloadUri, String Lf) {
    Log.d("url", "loadingProfileImage: " + Lf + downloadUri);
    Picasso.get()
      .load(downloadUri)
      .into(doctorImage);
    progressDialog.dismiss();
  }
}
