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
        Service temp1 = new Service("Help", 10.35);
        temp1.addProvider(new Provider("Name", "password"));
        temp1.addProvider(new Provider("sdfa", "password"));
        temp1.addProvider(new Provider("2342w", "password"));

        Service temp2 = new Service("dfvsdfvfsd", 10.35);
        temp1.addProvider(new Provider("Ngkjlg67me", "password"));
        temp1.addProvider(new Provider("s67a", "password"));
        temp1.addProvider(new Provider("2347w", "password"));
        data.add(temp1);
        data.add(temp2);

        recyclerView = findViewById(R.id.serviceRecyclerView);

        recyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Specify an adapter (see also next example)
        ServiceAdapter adapter = new ServiceAdapter(data);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
