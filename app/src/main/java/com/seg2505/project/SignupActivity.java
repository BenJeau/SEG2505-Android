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

    DatabaseReference databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button a  = (Button) findViewById(R.id.createUserButton);

        a.setOnClickListener(new View.OnClickListener() {
            Person ac;
            @Override
            public void onClick(View view) {
                EditText username = (EditText) findViewById(R.id.usernameEditText);
                EditText psswrd = (EditText) findViewById(R.id.passwordEditText);
                Spinner role = (Spinner)  findViewById(R.id.spinner);



                if (role.getSelectedItem() == "Admin"){
                    ac = new Admin(username.getText().toString(), psswrd.getText().toString());
                } else if(role.getSelectedItem() == "Service provider"){
                    ac = new Fournisseur(username.getText().toString(), psswrd.getText().toString());
                }else if(role.getSelectedItem() == "Proprietaire"){
                    ac = new Proprietaire(username.getText().toString(), psswrd.getText().toString());
                }
                System.out.println(role.getSelectedItem());
                TextView txt = (TextView) findViewById(R.id.textView);
                txt.setText("jjjjj");
                setContentView(R.layout.activity_login);
            }
        });

        }



    //databaseService = FirebaseDatabase.getInstance().getReference("products");

//    public void CreateUser(){
//        EditText username = (EditText) findViewById(R.id.usernameEditText);
//        EditText psswrd = (EditText) findViewById(R.id.passwordEditText);
//        Spinner role = (Spinner)  findViewById(R.id.spinner);
//
//
//
//        if (role.getSelectedItem() == "Admin"){
//             a = new Admin(username.getText().toString(), psswrd.getText().toString());
//        } else if(role.getSelectedItem() == "Service provider"){
//             a = new Fournisseur(username.getText().toString(), psswrd.getText().toString());
//        }else if(role.getSelectedItem() == "Proprietaire"){
//             a = new Proprietaire(username.getText().toString(), psswrd.getText().toString());
//        }
//        System.out.println(role.getSelectedItem());
//        TextView txt = (TextView) findViewById(R.id.textView);
//        txt.setText("jjjjj");
//        setContentView(R.layout.activity_login);
//    }
}
