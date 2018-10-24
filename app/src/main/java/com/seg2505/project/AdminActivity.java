package com.seg2505.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }
    public void CreateUser(View view){
        EditText username = (EditText) findViewById(R.id.usernameEditText);
        EditText psswrd = (EditText) findViewById(R.id.passwordEditText);
        Spinner role = (Spinner)  findViewById(R.id.spinner);


        if (role.getSelectedItem() == "Admin"){
            Admin a = new Admin(username.getText().toString(), psswrd.getText().toString());
        } else if(role.getSelectedItem() == "Service provider"){
            Fournisseur f = new Fournisseur(username.getText().toString(), psswrd.getText().toString());
        }else if(role.getSelectedItem() == "Proprietaire"){
            Proprietaire p = new Proprietaire(username.getText().toString(), psswrd.getText().toString());
        }
    }
}
