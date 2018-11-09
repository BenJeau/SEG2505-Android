package com.seg2505.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
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
import com.google.firebase.internal.InternalTokenResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    TextView btnSignUp;
    Button btnSignIn;
    EditText edtUsername, edtPassword;
    DatabaseReference userReference, adminReference;
    FirebaseDatabase database;

    public static final String INTENT_KEY_NAME = "name";
    public static final String INTENT_KEY_ROLE = "role";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignIn = findViewById(R.id.loginButton);
        btnSignUp = findViewById(R.id.explanation);
        edtUsername = findViewById(R.id.usernameEditText);
        edtPassword = findViewById(R.id.passwordEditText);

        database = FirebaseDatabase.getInstance();

        userReference = database.getReference("users");
        adminReference = database.getReference("Admin");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = edtUsername.getText().toString();
                final String password = edtPassword.getText().toString();

                if (username.equals("admin") && password.equals("admin")) {
                    Toast.makeText(getApplicationContext(), "Admin successfully logged in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,OptionActivity.class);
                    intent.putExtra("user", username );
                    startActivity(intent);
                } else {
                    if (TextUtils.isEmpty(username)) {
                        edtUsername.setError("User field cannot be empty.");
                    }
                    if (username.length() < 3 && !TextUtils.isEmpty(username)) {
                        edtUsername.setError("Username must be 3 characters long.");
                    }
                    if (TextUtils.isEmpty(password)) {
                        edtPassword.setError("Password field cannot be empty.");
                    }
                    if (!isValidPassword(password) && !TextUtils.isEmpty(password)) {
                        edtPassword.setError("Password must be a minimum of 4 characters and have at least one capital letter and one number.");
                    }

                    if (username.length() > 2 && !TextUtils.isEmpty(username) && isValidPassword(password)) {
                        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Red_Dialog);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("Authenticating...");
                        progressDialog.show();
                        final Handler handler = new Handler();
                        final Runnable r = new Runnable() {

                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        Person user = null;
                                        boolean exists = false;
                                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                            user = postSnapshot.getValue(Person.class);

                                            if (username.equals(user.getUsername())) {
                                                exists = true;
                                                break;
                                            }
                                        }

                                        if (!exists) {
                                            Toast.makeText(getApplicationContext(), "Wrong login, username not found", Toast.LENGTH_SHORT).show();
                                        } else if (user.getPassword().equals(password)) {
                                            Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                                            intent.putExtra(INTENT_KEY_NAME, user.getUsername());
                                            intent.putExtra(INTENT_KEY_ROLE, user.getRole());
                                            startActivity(intent);
                                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        } else if (!password.equals(user.getPassword())) {
                                            Toast.makeText(getApplicationContext(), "Wrong login, wrong password", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        };
                        handler.postDelayed(r, 1000);
                    }
                }

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });
    }

    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public static boolean isValidUsername(final String username) {
        Pattern pattern;
        Matcher matcher;

        final String USERNAME_PATTERN = "^(?=\\S+$).{3,}$";

        pattern = Pattern.compile(USERNAME_PATTERN);
        matcher = pattern.matcher(username);

        return matcher.matches();
    }

    public static boolean isValidAdministrator(String adminUsername, String adminPassword) {
        return adminUsername.equals("admin") && adminPassword.equals("admin");
    }
}