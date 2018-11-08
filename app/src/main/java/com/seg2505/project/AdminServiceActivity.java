package com.seg2505.project;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class AdminServiceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_service);

        // Gets the information from firebase
        final ArrayList<Service> data = new ArrayList<Service>();
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

        recyclerView = findViewById(R.id.serviceRecyclerView);

        recyclerView.setHasFixedSize(false);

        // Use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Specify an adapter (see also next example)
        ServiceAdapter adapter = new ServiceAdapter(data, this);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
