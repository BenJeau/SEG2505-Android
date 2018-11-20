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
import com.seg2505.project.model.ProviderInfo;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProviderInfoActivity  extends AppCompatActivity {

    Button BtnSubmitInfo;
    EditText edtStreetNumber, edtStreetName, edtCityName, edtProvinceName, edtCountryName, edtPostalCode, edtPhoneNumber, edtCompanyName, edtDescription;
    CheckBox edtLicensedCheckbox;
    //DatabaseReference databaseReference;
    FirebaseDatabase database;
    private Provider provider;
    private DatabaseReference userReference;
    private String userId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_info);

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
        edtLicensedCheckbox = findViewById(R.id.licensedCheckbox);
        userId=LoggedUser.id;

        database = FirebaseDatabase.getInstance();
        userReference = database.getReference().child("users").child(userId);


        //databaseReference = database.getReference("provider");


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
                final boolean licensedCheckbox = edtLicensedCheckbox.isChecked();


                if (TextUtils.isEmpty(streetNumber)) {
                    edtStreetNumber.setError("Street number field cannot be empty.");
                }
                if (isValidStreetNum(streetNumber) && !TextUtils.isEmpty(streetNumber)) {
                    edtStreetNumber.setError("Street number must be an integer");
                }
                if (TextUtils.isEmpty(streetName)) {
                    edtStreetName.setError("Street name field cannot be empty.");
                }
                if (isValidStreetName(streetName) && !TextUtils.isEmpty(streetName)) {
                    edtStreetName.setError("Street name must use valid characters: letters, numbers, spaces and hyphens.");
                }
                if (TextUtils.isEmpty(cityName)) {
                    edtCityName.setError("City name field cannot be empty.");
                }
                if (isValidName(cityName) && !TextUtils.isEmpty(cityName)) {
                    edtCityName.setError("City name must use valid characters: letters and hyphens.");
                }
                if (TextUtils.isEmpty(provinceName)) {
                    edtProvinceName.setError("Province name field cannot be empty.");
                }
                if (isValidName(provinceName) && !TextUtils.isEmpty(provinceName)) {
                    edtProvinceName.setError("Province name must use valid characters: letters and hyphens.");
                }
                if (TextUtils.isEmpty(countryName)) {
                    edtCountryName.setError("Country name field cannot be empty.");
                }
                if (isValidName(countryName) && !TextUtils.isEmpty(countryName)) {
                    edtCountryName.setError("Country name must use valid characters: letters and hyphens.");
                }
                if (TextUtils.isEmpty(postalCode)) {
                    edtPostalCode.setError("Postal code field cannot be empty.");
                }
                if (isValidPostalCode(postalCode) && !TextUtils.isEmpty(postalCode)) {
                    edtPostalCode.setError("Postal code must follow specific pattern: X1X1X1. \n X: A valid upercase letter. \n 1: A valid number.");
                }
                if (TextUtils.isEmpty(phoneNumber)) {
                    edtPhoneNumber.setError("Phone number field cannot be empty.");
                }
                if (isValidPhoneNumber(phoneNumber) && !TextUtils.isEmpty(phoneNumber)) {
                    edtPhoneNumber.setError("Phone number must follow specific pattern: XXX-XXX-XXXX. \n X: a valid number.");
                }
                if (TextUtils.isEmpty(companyName)) {
                    edtCompanyName.setError("Company name field cannot be empty.");
                }
                if (isValidCompanyName(companyName) && !TextUtils.isEmpty(companyName)) {
                    edtCompanyName.setError("Country name must use valid characters: letters, spaces and hyphens.");
                }
                if (isValidDescription(description)) {
                    edtDescription.setError("Cannot exceed limit of 300 characters.");
                }
                if (/*isValidStreetName(streetName) && isValidName(cityName) && isValidName(provinceName) && isValidName(countryName) &&
                        isValidPostalCode(postalCode) && isValidPhoneNumber(phoneNumber) && isValidCompanyName(companyName) && isValidDescription(description)*/
                        true) {

                    userReference.addListenerForSingleValueEvent(new ValueEventListener(){

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            provider = dataSnapshot.getValue(Provider.class);
                            ProviderInfo providerInfo = new ProviderInfo(phoneNumber, companyName, description, licensedCheckbox);
                            int streetNum = Integer.parseInt(streetNumber);
                            Address address = new Address(streetNum, streetName, cityName, provinceName, countryName);
                            provider.setAddress(address);
                            provider.setInfo(providerInfo);
                            userReference.setValue(provider);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    final ProgressDialog progressDialog = new ProgressDialog(ProviderInfoActivity.this, R.style.AppTheme_Blue_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Creating account...");
                    progressDialog.show();
                    final Handler handler = new Handler();
                    final Runnable r = new Runnable() {

                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Account information successfully added", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ProviderInfoActivity.this, ProviderHomeActivity.class);
                            startActivity(intent);
                        }
                    };

                    // TODO : Add new provider info and address to database
                    /*provider = getInfoDatabase();
                    ProviderInfo providerInfo = new ProviderInfo(phoneNumber, companyName, description, licensedCheckbox);
                    int streetNum = Integer.parseInt(streetNumber);
                    Address address = new Address(streetNum, streetName, cityName, provinceName, countryName);
                    provider.setAddress(address);
                    provider.setInfo(providerInfo);
                    userId = provider.getId();
                    userReference.setValue(provider);*/

                    handler.postDelayed(r, 1000);
                }

            }
        });

    }

    private Provider getInfoDatabase() {
        String userId = LoggedUser.id;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
       DatabaseReference userReference = database.getReference().child("users").child(userId);

        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                provider = dataSnapshot.getValue(Provider.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return provider;
    }

        public static boolean isValidStreetNum(String streetNum) {
            try {
                Integer.parseInt(streetNum);
            } catch(NumberFormatException e) {
                return false;
            }
            return true;
        }

        public static boolean isValidStreetName(String streetName) {
           Pattern p = Pattern.compile("[^a-z0-9- ]", Pattern.CASE_INSENSITIVE);
           Matcher m = p.matcher(streetName);
           boolean b = m.find();
           return !b;
        }

        public static boolean isValidPostalCode(String postalCode) {
            if (postalCode.length() != 6) {
                return false;
            }
            for (int i = 0; i < postalCode.length(); i++) {
                if (i%2 == 0) {
                    if(!Character.isLetter(postalCode.charAt(i))){
                        return false;
                    } else if (!Character.isUpperCase(postalCode.charAt(i))) {
                        return false;
                    }
                } else {
                    if(!Character.isDigit(postalCode.charAt(i))) {
                        return false;
                    }
                }
            }
            return true;
        }

        public static boolean isValidName(String name) {
            if(!Character.isUpperCase(name.charAt(0))) {
                return false;
            }
            Pattern p = Pattern.compile("[^a-z- ]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(name);
            boolean b = m.find();
            return !b;
        }

        public static boolean isValidCompanyName(String companyName) {
            Pattern p = Pattern.compile("[^a-z-. ]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(companyName);
            boolean b = m.find();
            return !b;
        }

        public static boolean isValidDescription(String description) {
            if(description.length() > 300) {
                return false;
            }
            return true;
        }

        public static boolean isValidPhoneNumber(String phoneNumber) {
            if(phoneNumber.matches("[0-9]+") && phoneNumber.length() == 10 ) {
                return true;
            }
            return false;
        }

        public static boolean empty(String string) {
            if(string.trim().isEmpty()) {
                return false;
            }
            return true;
        }


}
