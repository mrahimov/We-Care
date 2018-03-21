package com.example.murodjonrahimov.wecare;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.murodjonrahimov.wecare.fragments.DoctorsForumFragment;
import com.example.murodjonrahimov.wecare.fragments.AllPatientsPostsFragment;
import com.example.murodjonrahimov.wecare.fragments.DoctorProfileFragment;

public class DoctorActivity extends AppCompatActivity implements DoctorsForumFragment.onClickListenerDoc{

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_activity);

        toolbar = getSupportActionBar();
        loadFragment(new DoctorsForumFragment());

        BottomNavigationView navigation = findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                android.support.v4.app.Fragment fragment;

                switch (item.getItemId()) {
                    case R.id.navigation_doctors:
                        toolbar.setTitle("Doctors");
                        fragment = new DoctorsForumFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_my_profile:
                        toolbar.setTitle("Doctor Profile");
                        fragment = new DoctorProfileFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_posts:
                        toolbar.setTitle("Posts");
                        fragment = new AllPatientsPostsFragment();
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



    @Override
    public void onclick(String key) {

    }
}


