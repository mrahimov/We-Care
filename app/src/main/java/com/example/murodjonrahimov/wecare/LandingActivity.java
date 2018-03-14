package com.example.murodjonrahimov.wecare;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

// Very first activity for sign-in
// To Do Volha

public class LandingActivity extends AppCompatActivity {

    private Button registerButton;
    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_activity);

        registerButton = findViewById(R.id.register_button);
        signInButton = findViewById(R.id.sign_in_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // To Do Volha
            }
        });
    }
}
