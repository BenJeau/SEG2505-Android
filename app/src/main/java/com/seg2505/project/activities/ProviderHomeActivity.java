package com.seg2505.project.activities;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seg2505.project.R;
import com.seg2505.project.adapters.AdminRecyclerViewAdapter;
import com.seg2505.project.adapters.AvailabilityAdapter;
import com.seg2505.project.adapters.ProviderRecyclerViewAdapter;
import com.seg2505.project.adapters.ServiceAdapter;
import com.seg2505.project.model.Availability;
import com.seg2505.project.model.LoggedUser;
import com.seg2505.project.model.Person;
import com.seg2505.project.model.Provider;
import com.seg2505.project.model.Service;

import java.util.ArrayList;
import java.util.List;

public class ProviderHomeActivity extends AppCompatActivity {

    private Provider provider;
    private DatabaseReference userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_home);

        getInfoDatabase();

        Button button = findViewById(R.id.availabilitiesButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProviderHomeActivity.this, ProviderAvailabilityActivity.class);
                ProviderHomeActivity.this.startActivity(intent);
            }
        });
    }

    private void getInfoDatabase() {
        String userId = LoggedUser.id;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userReference = database.getReference().child("users").child(userId);

        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                provider = dataSnapshot.getValue(Provider.class);
                getData(provider);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onResume() {
        getInfoDatabase();
        super.onResume();
    }

    private void getData(Provider provider1) {

        if (provider == null) {
            provider = provider1;
        }
    
        if (provider.getAvailabilities() != null &&
                !provider.getAvailabilities().isEmpty()) {
            StringBuilder timesText = new StringBuilder();
            for (int i = 0; i < provider.getAvailabilities().size(); i++) {
                timesText.append(provider.getAvailabilities().get(i));
                if (i != provider.getAvailabilities().size() - 1) {
                    timesText.append("\n");
                }
            }

            TextView availabilities = findViewById(R.id.availabilities);
            availabilities.setText(timesText.toString());
        }

        if (provider.getServices() == null) {
            provider.createServices();
        }

        populateServiceRecyclerView(provider.getServices());
    }

    private void populateServiceRecyclerView(List<Service> data) {

        // Gets the recycler view and sets it to have a dataset that may vary in size
        RecyclerView recyclerView = findViewById(R.id.serviceRecyclerView);
        recyclerView.setHasFixedSize(false);

        // Uses a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProviderHomeActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        // Specify an adapter (see also next example)
        ProviderRecyclerViewAdapter adapter = new ProviderRecyclerViewAdapter(data);
        recyclerView.setAdapter(adapter);
    }
}