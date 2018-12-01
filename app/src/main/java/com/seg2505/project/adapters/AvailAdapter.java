package com.seg2505.project.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seg2505.project.R;
import com.seg2505.project.model.Availability;
import com.seg2505.project.model.Provider;
import com.seg2505.project.model.Service;


import java.util.ArrayList;
import java.util.List;

/**
 * Diedrick Ng
 * Kame House
 */
public class AvailAdapter extends RecyclerView.Adapter<AvailAdapter.MyViewHolder> {


    /**
     * The dataset/database used by the recycler view
     */

    private  List<Availability> mdata;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private RadioButton radioButton;



        private MyViewHolder(View v) {
            super(v);

            radioButton = v.findViewById(R.id.radioButton);


        }
    }

    public DatabaseReference serviceReference;
    private FirebaseDatabase database;


    public AvailAdapter(List<Availability> mdataset) {

       this.mdata = mdataset;

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        serviceReference = database.getReference().child("services");
//
//        for (Service service : mdataset) {
//            if (!provider.getServices().contains(service.getServiceId())) {
//                serviceReference.child(service.getServiceId()).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        dataset.add(dataSnapshot.getValue(Service.class));
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });
//            }
//        }
    }


    @Override

    public AvailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_service_recyler_view, parent, false);
        return new AvailAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AvailAdapter.MyViewHolder holder, final int position) {
        holder.radioButton.setText(mdata.get(position).getDay() + " : " + mdata.get(position).getTime());

//        holder.addIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                add(position);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }


}
