package com.seg2505.project.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.content.DialogInterface;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seg2505.project.R;
import com.seg2505.project.TabbedDialog;
import com.seg2505.project.adapters.AvailabilityAdapter;
import com.seg2505.project.interfaces.Cancelable;
import com.seg2505.project.model.Availability;
import com.seg2505.project.model.LoggedUser;
import com.seg2505.project.model.Provider;

import java.util.ArrayList;


public class ProviderAvailabilityActivity extends AppCompatActivity implements Cancelable {

    private AvailabilityAdapter adapter;
    static public DatabaseReference userReference;
    private FirebaseDatabase database;
    private String userId;
    private Provider provider;
    private TabbedDialog dialogFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_availability);
        userId=LoggedUser.id;

        // Gets the information from firebase
        final ArrayList<Availability> data = new ArrayList<Availability>();
        database = FirebaseDatabase.getInstance();
        userReference = database.getReference().child("users").child(userId);

        // TODO : Get information from firebase and replace it with the information below
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                provider = dataSnapshot.getValue(Provider.class);
                if(provider.getAvailabilities()==null){
                    provider.createAvailabilities();
                }
                Log.i("tagg",provider.getUsername());
                for (int i =0;i<provider.getAvailabilities().size();i++){
                    data.add(provider.getAvailabilities().get(i));
                }

                // Gets the recycler view and sets it to have a dataset that may vary in size
                RecyclerView recyclerView = findViewById(R.id.availabilitiesRecyclerView);
                recyclerView.setHasFixedSize(false);

                // Uses a linear layout manager
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProviderAvailabilityActivity.this);
                recyclerView.setLayoutManager(layoutManager);

                // Specify an adapter (see also next example)
                adapter = new AvailabilityAdapter(data, ProviderAvailabilityActivity.this);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });






        // A floating action button which helps the user to add a service
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateDialog1();
            }
        });
    }

    private void onCreateDialog1() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        dialogFragment = new TabbedDialog();
        dialogFragment.show(ft,"dialog");
        dialogFragment.setCancelable(this);


    }


    public void onCreateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.create_availability, null);
        final EditText time = view.findViewById(R.id.time);
        final EditText day = view.findViewById(R.id.day);

        builder.setTitle("Add Availability");
        builder.setView(view);
        builder.setPositiveButton("Create",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.setNegativeButton("Cancel", null);

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dayText = day.getText().toString();
                String timeText = time.getText().toString();

                if (true) {
                    final Availability availability = new Availability(dayText, timeText);
                    provider.addAvailabity(availability);
                    userReference.setValue(provider);
                    adapter.add(availability);
                    dialog.dismiss();

                    // TODO : Integrate firebase database here (when adding availability)
                }
            }
        });
    }


    @Override
    public void setCanceled(Boolean canceled1,String day,String time) {
        if (canceled1){
            Log.i("tagged","dddddd");

        }
        else{
            final Availability availability = new Availability(day, time);
            provider.addAvailabity(availability);
            userReference.setValue(provider);
            adapter.add(availability);
        }
        dialogFragment.dismiss();


    }
}
