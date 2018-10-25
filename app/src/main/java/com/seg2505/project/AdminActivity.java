package com.seg2505.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        ArrayList<Person> data = new ArrayList<Person>();
        Provider temp = new Provider("Ben", "12315dfC");
        temp.role = "Owner";
        data.add(temp);

        mRecyclerView = (RecyclerView) findViewById(R.id.adminRecyclerView);

        mRecyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Specify an adapter (see also next example)
        mAdapter = new AdminRecyclerViewAdapter(data);
        mRecyclerView.setAdapter(mAdapter);
    }
}
