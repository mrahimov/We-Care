package com.example.murodjonrahimov.wecare;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.murodjonrahimov.wecare.fragments.DDoctorsFragment;
import com.example.murodjonrahimov.wecare.fragments.DPostsFragment;
import com.example.murodjonrahimov.wecare.fragments.DProfileFragment;

// main activity for patients where Doctors, Posts & Profile Fragments interchange

public class PatientActivity extends AppCompatActivity {

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_activity);

        toolbar = getSupportActionBar();
        toolbar.setTitle("Doctors");
        loadFragment(new DDoctorsFragment());

        BottomNavigationView navigation = findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                android.support.v4.app.Fragment fragment;

                switch (item.getItemId()) {
                    case R.id.navigation_doctors:
                        toolbar.setTitle("Doctors");
                        fragment = new DDoctorsFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_my_profile:
                        toolbar.setTitle("Profile");
                        fragment = new DProfileFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_posts:
                        toolbar.setTitle("Posts");
                        fragment = new DPostsFragment();
                        loadFragment(fragment);
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
}
