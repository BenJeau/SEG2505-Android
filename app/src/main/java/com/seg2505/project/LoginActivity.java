package com.seg2505.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    Button signup;
    Button login;
    EditText  username;
    EditText psswrd;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.usernameEditText);
        psswrd = (EditText) findViewById(R.id.passwordEditText);



        login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
//            Intent intent = getIntent();
//            String user = intent.getStringExtra("user");
//            String psswrd1 = intent.getStringExtra("psswrd");

            @Override
            public void onClick(View view) {
                Intent i  = new Intent(LoginActivity.this, WelcomeActivity.class);
                LoginActivity.this.startActivity(i);
//                if(username.getText().equals("dngen049") && psswrd.getText().equals("allo")){
//                    Intent i  = new Intent(LoginActivity.this, WelcomeActivity.class);
//                    LoginActivity.this.startActivity(i);
//                }
            }
        });

        signup = (Button) findViewById(R.id.signupButton  );
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                LoginActivity.this.startActivity(intent);

            }
        });

    }


}
