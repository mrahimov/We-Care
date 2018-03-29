package com.example.murodjonrahimov.wecare.fragments;

import android.content.Intent;
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
import android.widget.TextView;

import com.example.murodjonrahimov.wecare.LoginActivity;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.DoctorProfileForm;
import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.model.Comment;
import com.example.murodjonrahimov.wecare.model.Doctor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DoctorProfileFragment extends Fragment {

    private TextView firstNameED;
    private TextView lastNameED;
    private TextView countryED;
    private TextView majorED;
    private TextView yearsOfExperienceED;
    private TextView type;
    private TextView numberOfDoctorsComments;
    private FloatingActionButton fab;
    private DatabaseReference db;
    private int count;

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
        fab = rootView.findViewById(R.id.add_fab);
        numberOfDoctorsComments = rootView.findViewById(R.id.number_of_doctors_comments);

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = Database.getDatabase();
        final String userID = Database.getUserId();

        db.child("doctors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                    if (dataSnapshot2.getKey().equals(userID)) {
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

        countDoctorsCommentsToPatientsPosts();

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
                FirebaseAuth.getInstance().signOut();
                getActivity().finishAffinity();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.language:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void countDoctorsCommentsToPatientsPosts() {

        db.child("comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot comment : dataSnapshot.getChildren()) {
                    Comment comment1 = comment.getValue(Comment.class);
                    String commentPostedBy = comment1.getCommentPostedByUserName();
                    if (commentPostedBy != null && commentPostedBy.equals(firstNameED.getText() + " " + lastNameED.getText())) {
                        count += 1;
                    }
                }
                numberOfDoctorsComments.setText(String.valueOf(count));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

