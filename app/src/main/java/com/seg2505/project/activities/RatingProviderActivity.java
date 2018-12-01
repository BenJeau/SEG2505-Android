package com.seg2505.project.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seg2505.project.R;
import com.seg2505.project.adapters.OwnerHomeAdapter;
import com.seg2505.project.model.LoggedUser;
import com.seg2505.project.model.Provider;
import com.seg2505.project.model.Address;
import com.seg2505.project.model.ProviderInfo;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RatingProviderActivity  extends AppCompatActivity {

    Button BtnSubmitInfo;
    EditText edtComment;
    TextView edtProviderName;
    RatingBar edtRating;

    FirebaseDatabase database;
    private Provider provider;
    private DatabaseReference userReference;
    private String userId;
    private List<Double> ratings;
    private String comment;
    private String providerName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_provider);

        BtnSubmitInfo = findViewById(R.id.BtnSubmitInfo);
        edtRating = findViewById(R.id.ratingBar);
        edtComment = findViewById(R.id.rateComment);
        edtProviderName = findViewById(R.id.providerTitle);

        userId = getIntent().getExtras().getString(OwnerHomeAdapter.INTENT_PROVIDER, "");

        database = FirebaseDatabase.getInstance();
        userReference = database.getReference().child("users").child(userId);


        userReference.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                provider = dataSnapshot.getValue(Provider.class);

                providerName = provider.getUsername();
                edtProviderName.setText(providerName);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        BtnSubmitInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String description = edtComment.getText().toString();
                final double rating = edtRating.getNumStars();

                boolean error = false;


                if (!isValidDescription(description)) {
                    edtComment.setError("Cannot exceed limit of 300 characters.");
                    error = true;
                }

                if (!error) {

                    userReference.addListenerForSingleValueEvent(new ValueEventListener(){

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            provider = dataSnapshot.getValue(Provider.class);

                            ratings = provider.getRatings();
                            if(ratings == null){
                                ratings = provider.createRatings();
                            }
                            ratings.add(rating);
                            provider.setRatings(ratings);
                            comment = provider.getComment();
                            comment = comment + " | "+ description;
                            provider.setComment(comment);

                            userReference.setValue(provider);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    final ProgressDialog progressDialog = new ProgressDialog(RatingProviderActivity.this, R.style.AppTheme_Blue_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Submitting rating...");
                    progressDialog.show();
                    final Handler handler = new Handler();
                    final Runnable r = new Runnable() {

                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Rating was successfully completed", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(RatingProviderActivity.this, OwnerActivity.class);
                            startActivity(intent);
                        }
                    };

                    handler.postDelayed(r, 1000);
                }

            }
        });
    }


    public static boolean isValidDescription(String description) {
        if(description.length() > 300) {
            return false;
        }
        return true;
    }

    public static boolean empty(String string) {
        if(string.trim().isEmpty()) {
            return false;
        }
        return true;
    }


}
