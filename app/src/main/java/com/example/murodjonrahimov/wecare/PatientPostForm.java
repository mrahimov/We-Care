package com.example.murodjonrahimov.wecare;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Post;

public class PatientPostForm extends AppCompatActivity {

    private EditText messageED;
    private EditText addedByED;
    private EditText timestampED;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_post_form);

        saveButton = findViewById(R.id.save_button);
        messageED = findViewById(R.id.message_ed);
        addedByED = findViewById(R.id.added_by_ed);
        timestampED = findViewById(R.id.timestamp_ed);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = messageED.getText().toString();
                String addedBy = addedByED.getText().toString();
                String timestamp = timestampED.getText().toString();

                Post post = new Post(message, addedBy, timestamp);
                Database.savePost(post);
            }
        });

    }

}
