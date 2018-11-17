package com.seg2505.project.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.seg2505.project.adapters.AdminRecyclerViewAdapter;
import com.seg2505.project.adapters.ServiceAdapter;
import com.seg2505.project.model.Provider;
import com.seg2505.project.model.Service;

import java.util.ArrayList;

public class AdminServiceActivity extends AppCompatActivity {

    private ServiceAdapter adapter;
    static public DatabaseReference serviceReference;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_service);

        // Gets the information from firebase
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
                // Gets the recycler view and sets it to have a dataset that may vary in size
                RecyclerView recyclerView = findViewById(R.id.serviceRecyclerView);
                recyclerView.setHasFixedSize(false);

                // Uses a linear layout manager
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AdminServiceActivity.this);
                recyclerView.setLayoutManager(layoutManager);

                // Specify an adapter (see also next example)
                adapter = new ServiceAdapter(data, AdminServiceActivity.this);
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
                onCreateDialog();
            }
        });
    }

    public void onCreateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.create_service, null);
        final EditText hourlyRate = view.findViewById(R.id.hourlyRate);
        final EditText serviceName = view.findViewById(R.id.serviceName);

        builder.setTitle("Add Service");
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
                String serviceText = serviceName.getText().toString();
                String hourlyRateText = hourlyRate.getText().toString();

                if (validateDialog(serviceName, hourlyRate, serviceText, hourlyRateText)) {
                    String id = serviceReference.push().getKey();
                    final Service service = new Service(serviceText, Double.parseDouble(hourlyRateText),id);
                    service.addProvider(new Provider("", "",""));
                    /*service.addProvider(new Provider("Jen", "password"));
                    service.addProvider(new Provider("dd", "password"));*/
                    serviceReference.child(id).setValue(service);
                    adapter.add(service);
                    dialog.dismiss();

                    // TODO : Integrate firebase database here (when adding service)
                }
            }
        });
    }

    public static boolean validateDialog(EditText serviceName, EditText hourlyRate, String serviceText, String hourlyRateText) {
        boolean correctInfo = true;

        if (isFieldEmpty(serviceText)) {
            serviceName.setError("Service cannot be empty.");
            correctInfo = false;
        }

        if (isFieldEmpty(hourlyRateText)) {
            hourlyRate.setError("Hourly rate cannot be empty.");
            correctInfo = false;
        } else if (!isDouble(hourlyRate.getText().toString())) {
            hourlyRate.setError("Hourly rate has to be a double.");
            correctInfo = false;
        }

        return correctInfo;
    }

    public static boolean isFieldEmpty(String text) {
        return text == null || text.equals("") || text.trim().length()==0;
    }

    public static boolean isDouble(String hourlyRate) {
        try {
            Double.parseDouble(hourlyRate);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
