package com.seg2505.project.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seg2505.project.R;
import com.seg2505.project.model.LoggedUser;
import com.seg2505.project.model.Provider;
import com.seg2505.project.model.Address;
import com.seg2505.project.model.ProviderInfo;

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

                boolean error = false;


                if (TextUtils.isEmpty(streetNumber)) {
                    edtStreetNumber.setError("Street number field cannot be empty.");
                    error = true;
                } else if (!isValidStreetNum(streetNumber)) {
                    edtStreetNumber.setError("Street number must be a valid number");
                    error = true;
                }

                if (TextUtils.isEmpty(streetName)) {
                    edtStreetName.setError("Street name field cannot be empty.");
                    error = true;
                } else if (!isValidStreetName(streetName)) {
                    edtStreetName.setError("Street name must use valid characters: letters, numbers, spaces and hyphens.");
                    error = true;
                }

                if (TextUtils.isEmpty(cityName)) {
                    edtCityName.setError("City name field cannot be empty.");
                    error = true;
                } else if (isValidName(cityName)) {
                    edtCityName.setError("City name must use valid characters: letters and hyphens.");
                    error = true;
                }

                if (TextUtils.isEmpty(provinceName)) {
                    edtProvinceName.setError("Province name field cannot be empty.");
                    error = true;
                } else if (isValidName(provinceName)) {
                    edtProvinceName.setError("Province name must use valid characters: letters and hyphens.");
                    error = true;
                }

                if (TextUtils.isEmpty(countryName)) {
                    edtCountryName.setError("Country name field cannot be empty.");
                    error = true;
                } else if (isValidName(countryName)) {
                    edtCountryName.setError("Country name must start with Caps use valid characters: letters and hyphens.");
                    error = true;
                }

                if (TextUtils.isEmpty(postalCode)) {
                    edtPostalCode.setError("Postal code field cannot be empty.");
                    error = true;
                } else if (!isValidPostalCode(postalCode)) {
                    edtPostalCode.setError("Postal code must follow specific pattern: X1X1X1. \n X: A valid upercase letter. \n 1: A valid number.");
                    error = true;
                }

                if (TextUtils.isEmpty(phoneNumber)) {
                    edtPhoneNumber.setError("Phone number field cannot be empty.");
                    error = true;
                } else if (!isValidPhoneNumber(phoneNumber)) {
                    edtPhoneNumber.setError("Phone number must follow specific pattern: XXXXXXXXXX. \n X: A valid number.");
                    error = true;
                }

                if (TextUtils.isEmpty(companyName)) {
                    edtCompanyName.setError("Company name field cannot be empty.");
                    error = true;
                } else if (!isValidCompanyName(companyName)) {
                    edtCompanyName.setError("Country name must use valid characters: letters, spaces and hyphens.");
                    error = true;
                }

                if (!isValidDescription(description)) {
                    edtDescription.setError("Cannot exceed limit of 300 characters.");
                    error = true;
                }

                if (!error) {

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

                    handler.postDelayed(r, 1000);
                }

            }
        });
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
            if(!empty(name) || Character.isUpperCase(name.charAt(0))) {
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
