package com.seg2505.project.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seg2505.project.DatePickerFragment;
import com.seg2505.project.R;
import com.seg2505.project.TimePickerFragment;
import com.seg2505.project.adapters.AvailAdapter;
import com.seg2505.project.model.Availability;
import com.seg2505.project.model.Provider;

import java.util.Calendar;
import java.util.List;

public class BookingActivity extends AppCompatActivity {

    private  AvailAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
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
                }

            }
        });


        DatabaseReference serviceReference = FirebaseDatabase.getInstance().getReference("users");
        serviceReference.child("-LSRz1tVfbUvEzYpaDCq").addListenerForSingleValueEvent(new ValueEventListener() {
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


}
