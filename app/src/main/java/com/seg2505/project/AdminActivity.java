package com.seg2505.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    DatabaseReference userReference;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Gets the information from firebase
        final ArrayList<Person> data = new ArrayList<Person>();

        database = FirebaseDatabase.getInstance();
        userReference = database.getReference("users");
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Person user = postSnapshot.getValue(Person.class);
                    data.add(user);
                }

                createRecyclerView(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void createRecyclerView(ArrayList<Person> data) {
        mRecyclerView = findViewById(R.id.adminRecyclerView);

        mRecyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Specify an adapter (see also next example)
        mAdapter = new AdminRecyclerViewAdapter(data);
        mRecyclerView.setAdapter(mAdapter);
    }
}
