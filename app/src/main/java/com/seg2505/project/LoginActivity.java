package com.seg2505.project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button btnSignUp;
    Button btnSignIn;
    EditText edtUsername;
    EditText edtPassword;
    DatabaseReference userReference,adminReference,serviceProviderReference,proprietaireReference;
    FirebaseDatabase database;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnSignIn = (Button) findViewById(R.id.loginButton );

        btnSignUp = (Button) findViewById(R.id.signupButton  );
        edtUsername = (EditText) findViewById(R.id.usernameEditText);
        edtPassword = (EditText) findViewById(R.id.passwordEditText);
        database= FirebaseDatabase.getInstance();

        userReference = database.getReference("users");
        adminReference = userReference.child("admins");
        serviceProviderReference= userReference.child("service Providers");
        final DatabaseReference userReference1 = userReference.child("proprietaires");
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = edtUsername.getText().toString();
                final String password = edtPassword.getText().toString();
                adminReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            Admin admin = postSnapshot.getValue(Admin.class);
                            if (username.equals(admin.getEmail())&& password.equals(admin.getPassword())){
                                Intent intent = new Intent(LoginActivity.this,WelcomeActivity.class);
                                startActivity(intent);

                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

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
