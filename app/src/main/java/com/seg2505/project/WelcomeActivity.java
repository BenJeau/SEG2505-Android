package com.seg2505.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        String name = getIntent().getExtras().getString(LoginActivity.INTENT_KEY_NAME, "");
        String role = getIntent().getExtras().getString(LoginActivity.INTENT_KEY_ROLE, "");

        TextView welcomeMessage = findViewById(R.id.welcomeText);
        welcomeMessage.setText("Welcome " + name + "! You are logged in as a " + role);

    }
}
