package com.example.murodjonrahimov.wecare;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Post;
import java.text.SimpleDateFormat;

public class PatientPostForm extends AppCompatActivity {

  private EditText messageED;
  private Button saveButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.patient_post_form);

    saveButton = findViewById(R.id.save_button);
    messageED = findViewById(R.id.message_ed);

    saveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String message = messageED.getText()
          .toString();

        SharedPreferences preferences = getSharedPreferences(RegistrationActivity.WE_CARE_SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        final String postedByUserName = preferences.getString(RegistrationActivity.USERNAME_KEY, "");

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy h:mm a");
        String dateString = sdf.format(date);

        Post post = new Post(message, dateString, postedByUserName);
        Database.savePost(post);
        finish();
      }
    });
  }
}
