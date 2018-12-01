package com.seg2505.project.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.seg2505.project.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        String name = getIntent().getExtras().getString(LoginActivity.INTENT_KEY_NAME, "");
        final String role = getIntent().getExtras().getString(LoginActivity.INTENT_KEY_ROLE, "");
        final boolean hasInfo = getIntent().getExtras().getBoolean(LoginActivity.INTENT_KEY_HASINFO, false);

        TextView welcomeMessage = findViewById(R.id.welcomeText);
        welcomeMessage.setText("Welcome " + name + "! You are logged in as a " + role);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (role.equals("Provider")) {
                    Intent intent;

                    if (hasInfo) {
                        intent = new Intent(WelcomeActivity.this, ProviderHomeActivity.class);
                    } else {
                        intent = new Intent(WelcomeActivity.this, ProviderInfoActivity.class);
                    }

                    WelcomeActivity.this.startActivity(intent);
                }else {
                    Intent intent = new Intent(WelcomeActivity.this, bookingActivity.class);
                }
            }
        }, 2000);
    }
}
