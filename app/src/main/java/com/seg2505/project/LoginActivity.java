package com.seg2505.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    TextView btnSignUp;
    Button btnSignIn;
    EditText edtUsername;
    EditText edtPassword;
    DatabaseReference userReference;
    FirebaseDatabase database;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnSignIn = (Button) findViewById(R.id.loginButton );

        btnSignUp = (TextView) findViewById(R.id.explanation  );
        edtUsername = (EditText) findViewById(R.id.usernameEditText);
        edtPassword = (EditText) findViewById(R.id.passwordEditText);
        database= FirebaseDatabase.getInstance();

        userReference = database.getReference("users");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = edtUsername.getText().toString();
                final String password = edtPassword.getText().toString();

                if(TextUtils.isEmpty(username)) {
                    edtUsername.setError("User field cannot be empty");

                }
                if(username.length()<3 && !TextUtils.isEmpty(username)) {

                    edtUsername.setError("Username must be 3 characters long");
                }
                if(TextUtils.isEmpty(password)) {
                    edtPassword.setError("Password field cannot be empty");
                }

                if (username.length()>2 && !TextUtils.isEmpty(username)&& !TextUtils.isEmpty(password)){
                    userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                                Person user = postSnapshot.getValue(Admin.class);
                                if (username.equals(user.getEmail())&& password.equals(user.getPassword())){
                                    Toast.makeText(getApplicationContext(), "Successfully Logged in", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this,WelcomeActivity.class);
                                    startActivity(intent);

                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Wrong login", Toast.LENGTH_SHORT).show();
                                    edtPassword.getText().clear();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }






            }
        });



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                LoginActivity.this.startActivity(intent);

            }
        });

    }

}
