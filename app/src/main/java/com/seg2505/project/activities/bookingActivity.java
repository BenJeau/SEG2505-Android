package com.seg2505.project.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seg2505.project.R;
import com.seg2505.project.adapters.AvailAdapter;
import com.seg2505.project.adapters.ProviderRecyclerViewAdapter;
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
                populateServiceRecyclerView(availabilities);

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
    private void populateServiceRecyclerView(List<Availability> data) {

        // Gets the recycler view and sets it to have a dataset that may vary in size
        RecyclerView recyclerView = findViewById(R.id.userRecyclerView);
        recyclerView.setHasFixedSize(false);

        // Uses a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(bookingActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        // Specify an adapter (see also next example)
        AvailAdapter adapter = new AvailAdapter(data);
        recyclerView.setAdapter(adapter);
    }
}
