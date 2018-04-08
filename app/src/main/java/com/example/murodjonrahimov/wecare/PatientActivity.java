package com.example.murodjonrahimov.wecare;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.example.murodjonrahimov.wecare.fragments.ListOfDoctorsFragment;
import com.example.murodjonrahimov.wecare.fragments.PatientMyPostFragment;
import com.example.murodjonrahimov.wecare.fragments.PatientProfileFragment;
import com.example.murodjonrahimov.wecare.fragments.SearchQueryDoc;
import com.example.murodjonrahimov.wecare.fragments.SearchQueryFragment;

public class PatientActivity extends AppCompatActivity implements ListOfDoctorsFragment.SearchDoctorslistener{

  private ActionBar toolbar;
  private SearchQueryDoc searchQueryDoc;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.patient_activity);

    toolbar = getSupportActionBar();
    loadFragment(new ListOfDoctorsFragment());

    BottomNavigationView navigation = findViewById(R.id.navigation);

    navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        android.support.v4.app.Fragment fragment;

        switch (item.getItemId()) {
          case R.id.navigation_doctors:
            toolbar.setTitle("List of Doctors");
            fragment = new ListOfDoctorsFragment();
            loadFragment(fragment);
            return true;
          case R.id.navigation_my_profile:
            toolbar.setTitle("My Profile");
            fragment = new PatientProfileFragment();
            loadFragment(fragment);
            return true;
          case R.id.navigation_posts:
            toolbar.setTitle("My Posts");
            fragment = new PatientMyPostFragment();
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
  public void search(String s) {
    Bundle bundle = new Bundle();
    bundle.putString("search1", s);
    searchQueryDoc = new SearchQueryDoc();
    searchQueryDoc.setArguments(bundle);
    FragmentTransaction t = getSupportFragmentManager().beginTransaction();
    t.replace(R.id.frame_container, searchQueryDoc);
    t.addToBackStack("search");
    t.commit();
  }
}
