package com.seg2505.project.activities;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.seg2505.project.R;
import com.seg2505.project.adapters.AdminRecyclerViewAdapter;
import com.seg2505.project.adapters.ProviderRecyclerViewAdapter;
import com.seg2505.project.adapters.ServiceAdapter;
import com.seg2505.project.model.Person;
import com.seg2505.project.model.Service;

import java.util.ArrayList;

public class ProviderHomeActivity extends AppCompatActivity {

    private ProviderRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_home);

        ArrayList<String> times = new ArrayList<String>();
        times.add("Monday 15h30-16h30");
        times.add("Friday 10h30-16h00");
        times.add("Sunday 5h30-6h30");

        StringBuilder timesText = new StringBuilder();
        for (int i = 0; i < times.size(); i++) {
            timesText.append(times.get(i));
            if (i != times.size() - 1) {
                timesText.append("\n");
            }
        }

        if (timesText.length() != 0) {
            TextView availabilities = findViewById(R.id.availabilities);
            availabilities.setText(timesText.toString());
        }

        ArrayList<Service> data = new ArrayList<Service>();
        data.add(new Service("Electricity", 15.20, "famfo34498ysf"));
        data.add(new Service("Internet", 1.25, "famfo344etsdgdfsgds98ysf"));
        data.add(new Service("Water", 5.0, "dsfgds"));

        // Gets the recycler view and sets it to have a dataset that may vary in size
        RecyclerView recyclerView = findViewById(R.id.serviceRecyclerView);
        recyclerView.setHasFixedSize(false);

        // Uses a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProviderHomeActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        // Specify an adapter (see also next example)
        adapter = new ProviderRecyclerViewAdapter(data);
        recyclerView.setAdapter(adapter);
    }
}