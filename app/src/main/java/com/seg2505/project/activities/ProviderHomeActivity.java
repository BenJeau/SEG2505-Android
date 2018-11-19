package com.seg2505.project.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seg2505.project.R;

import com.seg2505.project.adapters.DialogAdapter;
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


    static public DatabaseReference serviceReference;
    private FirebaseDatabase database;

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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateDialog();
            }
        });
    }
//    public void onCreateDialog() {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        // Get the layout inflater
//        LayoutInflater inflater = this.getLayoutInflater();
//        View view = inflater.inflate(R.layout.add_services, null);
//
//        final AlertDialog dialog = builder.create();
//
//        builder.setTitle("Services");
//        builder.setView(view);
//        RecyclerView recyclerView = view.findViewById(R.id.serviceRecyclerView1);
//        recyclerView.setHasFixedSize(false);
//
//

//
//

//
//    }

    public void onCreateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.add_services, null);
        final  RecyclerView recyclerView = view.findViewById(R.id.serviceRecyclerView1);
        recyclerView.setHasFixedSize(false);

        builder.setTitle("Services");
        builder.setView(view);
        builder.setPositiveButton("Done",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.setNegativeButton("Cancel", null);


        final ArrayList<Service> data = new ArrayList<Service>();
        database = FirebaseDatabase.getInstance();
        serviceReference = database.getReference("services");

        // TODO : Get information from firebase and replace it with the information below
        serviceReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Service user = postSnapshot.getValue(Service.class);
                    data.add(user);
                }

                // Uses a linear layout manager
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProviderHomeActivity.this);
                recyclerView.setLayoutManager(layoutManager);

                // Specify an adapter (see also next example)
                DialogAdapter adapter = new DialogAdapter(data);
                recyclerView.setAdapter(adapter);




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dialog.dismiss();

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