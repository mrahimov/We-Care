package com.example.murodjonrahimov.wecare.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.murodjonrahimov.wecare.LoginActivity;
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

  public DoctorProfileFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.d_fragment_profile, container, false);
    setHasOptionsMenu(true);

    firstNameED = rootView.findViewById(R.id.first_name);
    lastNameED = rootView.findViewById(R.id.last_name);
    countryED = rootView.findViewById(R.id.country);
    majorED = rootView.findViewById(R.id.major);
    yearsOfExperienceED = rootView.findViewById(R.id.years_of_experience);
    type = rootView.findViewById(R.id.type_doctor);
    numberOfDoctorsComments = rootView.findViewById(R.id.number_of_doctors_comments);
    buttonDoctorImage = rootView.findViewById(R.id.button_doctor_image);
    fab = rootView.findViewById(R.id.add_fab);


    storageReference = FirebaseStorage.getInstance().getReference();
    doctorImage = rootView.findViewById(R.id.imageview_doctor_profile);
    progressDialog = new ProgressDialog(getContext());




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

      Uri uri = data.getData();

      assert uri != null;
      StorageReference docImage = storageReference.child(userID).child(uri.getAuthority());

      docImage.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

          progressDialog.dismiss();

          Uri downloadUri = taskSnapshot.getDownloadUrl();

          Picasso.get().load(downloadUri).into(doctorImage);


            Toast.makeText(getContext(), "upload done", Toast.LENGTH_LONG).show();
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
}
