package com.seg2505.project.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.LayoutInflater;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seg2505.project.R;
import com.seg2505.project.model.LoggedUser;
import com.seg2505.project.model.Person;
import com.seg2505.project.model.Provider;
import com.seg2505.project.model.Service;
import com.seg2505.project.model.Address;
import com.seg2505.project.model.Availability;

import java.util.ArrayList;

public class ProviderInfoActivity  extends AppCompatActivity {

    Button BtnSubmitInfo;
    EditText edtStreetNumber, edtStreetName, edtCityName, edtProvinceName, edtCountryName, edtPostalCode, edtPhoneNumber, edtCompanyName, edtDescription;
    CheckBox licensedCheckbox;
    DatabaseReference providerReference;
    FirebaseDatabase database;

    public static final String INTENT_KEY_streetNumber = "street number";
    public static final String INTENT_KEY_streetName = "street name";
    public static final String INTENT_KEY_cityName = "city name";
    public static final String INTENT_KEY_provinceName = "province name";
    public static final String INTENT_KEY_countryName = "country name";
    public static final String INTENT_KEY_postalCode = "postal code";
    public static final String INTENT_KEY_phoneNumber = "phone number";
    public static final String INTENT_KEY_companyName = "company name";
    public static final String INTENT_KEY_description = "description";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BtnSubmitInfo = findViewById(R.id.BtnSubmitInfo);
        edtStreetNumber = findViewById(R.id.streetNumber);
        edtStreetName = findViewById(R.id.streetName);
        edtCityName = findViewById(R.id.cityName);
        edtProvinceName = findViewById(R.id.provinceName);
        edtCountryName = findViewById(R.id.countryName);
        edtPostalCode = findViewById(R.id.postalCode);
        edtPhoneNumber = findViewById(R.id.phoneNumber);
        edtCompanyName = findViewById(R.id.companyName);
        edtDescription = findViewById(R.id.description);

        database = FirebaseDatabase.getInstance();

        providerReference = database.getReference("provider");
        adminReference = database.getReference("Admin");

        BtnSubmitInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String streetNumber = edtStreetNumber.getText().toString();
                final String streetName = edtStreetName.getText().toString();
                final String cityName = edtCityName.getText().toString();
                final String provinceName = edtProvinceName.getText().toString();
                final String countryName = edtCountryName.getText().toString();
                final String postalCode = edtPostalCode.getText().toString();
                final String phoneNumber = edtPhoneNumber.getText().toString();
                final String companyName = edtCompanyName.getText().toString();
                final String description = edtDescription.getText().toString();


                    if (TextUtils.isEmpty(streetNumber)) {
                        edtStreetNumber.setError("User field cannot be empty.");
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
                                            Intent intent = new Intent(LoginActivity.this, ProviderAvailabilityActivity.class);
                                            intent.putExtra(INTENT_KEY_NAME, user.getUsername());
                                            intent.putExtra(INTENT_KEY_ROLE, user.getRole());
                                            LoggedUser.setId(user.getId());
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
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProviderInfoActivity.this, ProviderHomeActivity.class);
                ProviderInfoActivity.this.startActivity(intent);
            }
        });
    }

}
