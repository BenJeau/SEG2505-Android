package com.seg2505.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }
    public void CreateUser(View view){
        EditText username = (EditText) findViewById(R.id.usernameEditText);
        EditText psswrd = (EditText) findViewById(R.id.passwordEditText);

    }
}
