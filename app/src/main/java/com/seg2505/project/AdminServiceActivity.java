package com.seg2505.project;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class AdminServiceActivity extends AppCompatActivity {

    private ServiceAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_service);

        // Gets the information from firebase
        final ArrayList<Service> data = new ArrayList<Service>();

        // TODO : Get information from firebase and replace it with the information below
        Service temp1 = new Service("Water", 10.87);
        temp1.addProvider(new Provider("Jen", "password"));
        temp1.addProvider(new Provider("Sofie", "password"));
        temp1.addProvider(new Provider("Lauren", "password"));

        Service temp2 = new Service("Electricity", 1.35);
        temp2.addProvider(new Provider("Vergenie", "password"));
        temp2.addProvider(new Provider("Diedrick", "password"));
        temp2.addProvider(new Provider("Alex", "password"));
        temp2.addProvider(new Provider("Rick", "password"));
        data.add(temp1);
        data.add(temp2);

        // Gets the recycler view and sets it to have a dataset that may vary in size
        RecyclerView recyclerView = findViewById(R.id.serviceRecyclerView);
        recyclerView.setHasFixedSize(false);

        // Uses a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Specify an adapter (see also next example)
        adapter = new ServiceAdapter(data, this);
        recyclerView.setAdapter(adapter);

        // A floating action button which helps the user to add a service
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = onCreateDialog();
                d.show();
            }
        });
    }

    public Dialog onCreateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.create_service, null);
        final EditText hourlyRate = view.findViewById(R.id.hourlyRate);
        final EditText serviceName = view.findViewById(R.id.serviceName);

        builder.setTitle("Add Service");

        builder.setView(view);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Service service = new Service(serviceName.getText().toString(), Double.parseDouble(hourlyRate.getText().toString()));
                adapter.add(service);
                // TODO : Add service firebase function here
                // TODO : Verify if the values makes sense
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {}
        });

        return builder.create();
    }
}
