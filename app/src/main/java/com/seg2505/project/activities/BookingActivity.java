package com.seg2505.project.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seg2505.project.adapters.OwnerHomeAdapter;
import com.seg2505.project.fragments.DatePickerFragment;
import com.seg2505.project.R;
import com.seg2505.project.fragments.TimePickerFragment;
import com.seg2505.project.adapters.AvailAdapter;
import com.seg2505.project.model.Availability;
import com.seg2505.project.model.LoggedUser;
import com.seg2505.project.model.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookingActivity extends AppCompatActivity {

    private AvailAdapter adapter;
    private Availability chosenAvaility;
    private String userId;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        userId = getIntent().getExtras().getString(OwnerHomeAdapter.INTENT_PROVIDER, "");

         final DatePickerFragment newfra = new DatePickerFragment();
        FloatingActionButton fab = findViewById(R.id.book);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompoundButton lastCheckedRB = adapter.getLastCheckedRB();

                if(lastCheckedRB == null){
                    Toast.makeText(getApplicationContext(), "Please choose something", Toast.LENGTH_SHORT).show();
                }else {
                    newfra.show(getSupportFragmentManager(), "timePicker");
                    chosenAvaility = adapter.getMdata().get((int)lastCheckedRB.getTag());

                }

            }
        });


        DatabaseReference serviceReference = FirebaseDatabase.getInstance().getReference("users");
        serviceReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            List<Availability> availabilities;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Provider provider = dataSnapshot.getValue(Provider.class);


                if (provider != null) {
                    availabilities = provider.getAvailabilities();
                    populateServiceRecyclerView(availabilities);

                }
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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BookingActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        // Specify an adapter (see also next example)
         adapter = new AvailAdapter(data);
        recyclerView.setAdapter(adapter);
    }
    public void Book(String s){
        Toast.makeText(getApplicationContext(), s +": " + chosenAvaility.getDay() + ": "+chosenAvaility.getTime(), Toast.LENGTH_SHORT).show();
        ArrayList<String> a = chosenAvaility.getOwnerid();

        chosenAvaility.add(LoggedUser.id,s);


    }


}
