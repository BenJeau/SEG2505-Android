package com.seg2505.project.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seg2505.project.R;
import com.seg2505.project.model.Availability;
import com.seg2505.project.model.Provider;
import com.seg2505.project.model.Service;

import java.util.List;

public class bookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);



        DatabaseReference serviceReference = FirebaseDatabase.getInstance().getReference("users");
        serviceReference.child("LSRz1tVfbUvEzYpaDCq").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Provider provider = dataSnapshot.getValue(Provider.class);
                List<Availability> availabilities = provider.getAvailabilities();

//                if (providers != null) {
//
//                    DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference("users");
//                    for (int i = 0; i < providers.size(); i++) {
//                        usersReference.child(providers.get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                Provider provider = dataSnapshot.getValue(Provider.class);
//
//
//                                if (provider != null) {
//                                    List<Availability> availabilities = provider.getAvailabilities();
//
//
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//                            }
//                        });
//                    }
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
