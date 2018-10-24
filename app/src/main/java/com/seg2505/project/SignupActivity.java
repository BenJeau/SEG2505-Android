package com.seg2505.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.Button;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity {

    DatabaseReference userReference, adminReference, serviceProviderReference;
    Button a;
    Spinner role;
    EditText username;
    EditText psswrd;
    TextView tx;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        a = (Button) findViewById(R.id.createUserButton);
        username = (EditText) findViewById(R.id.usernameEditText);
        psswrd = (EditText) findViewById(R.id.passwordEditText);
        role = (Spinner) findViewById(R.id.spinner);
        tx = (TextView) findViewById(R.id.textView);

        database = FirebaseDatabase.getInstance();

        userReference = database.getReference("users");
        adminReference = userReference.child("admins");
        serviceProviderReference = userReference.child("service Providers");
        final DatabaseReference userReference1 = userReference.child("proprietaires");


        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tx.setText("jjjjj");
                Person ac = null;


                if (role.getSelectedItem().equals("Admin")) {
                    ac = new Admin(username.getText().toString(), psswrd.getText().toString());
                    adminReference.push().setValue(ac);
                } else if (role.getSelectedItem().equals("Prodiver")) {
                    ac = new Fournisseur(username.getText().toString(), psswrd.getText().toString());
                    serviceProviderReference.push().setValue(ac);
                } else if (role.getSelectedItem().equals("User")) {
                    ac = new Proprietaire(username.getText().toString(), psswrd.getText().toString());
                    userReference1.push().setValue(ac);
                }

            }
        });

    }

}