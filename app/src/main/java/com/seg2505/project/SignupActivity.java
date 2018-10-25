package com.seg2505.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.Button;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    Button a;
    Spinner role;
    EditText edtUsername;
    EditText edtPassword;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        a = (Button) findViewById(R.id.signupButton);
        edtUsername = (EditText) findViewById(R.id.usernameEditText);
        edtPassword = (EditText) findViewById(R.id.passwordEditText);
        role = (Spinner) findViewById(R.id.dropdown);

        database = FirebaseDatabase.getInstance();


        databaseReference = database.getReference("users");


        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if(TextUtils.isEmpty(username)) {
                    edtUsername.setError("User field cannot be empty");
                }
                if(TextUtils.isEmpty(password)) {
                    edtPassword.setError("Password field cannot be empty");
                }
                if(username.length()<3 && !TextUtils.isEmpty(username)) {

                    edtUsername.setError("Username must be 3 characters long");
                }
                if (!isValidPassword(password) &&!TextUtils.isEmpty(password) ){
                    edtPassword.setError("Password must be minimum 4 characters and have atleast one Capital letter and one number");
                }
                Person ac = null;

                if (username.length()>2 && isValidPassword(password)){
                    if (role.getSelectedItem().equals("Provider")) {
                        ac = new Fournisseur(username, password);
                    } else if (role.getSelectedItem().equals("User")) {
                        ac = new Proprietaire(username, password);
                    }
                    databaseReference.push().setValue(ac);
                    Toast.makeText(getApplicationContext(), "Account successfully created", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                    startActivity(intent);
                }



            }
        });

    }

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

}