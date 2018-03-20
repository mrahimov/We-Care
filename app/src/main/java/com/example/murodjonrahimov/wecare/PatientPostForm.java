package com.example.murodjonrahimov.wecare;


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
    private EditText addedByED;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_post_form);

        saveButton = findViewById(R.id.save_button);
        messageED = findViewById(R.id.message_ed);
        addedByED = findViewById(R.id.added_by_ed);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = messageED.getText().toString();
                String addedBy = addedByED.getText().toString();

                long date = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy h:mm a");
                String dateString = sdf.format(date);

                Post post = new Post(message, addedBy, dateString);
                Database.savePost(post);
                finish();
            }
        });

    }

}
