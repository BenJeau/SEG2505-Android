package com.seg2505.project.adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
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
import com.seg2505.project.model.Booking;
import com.seg2505.project.model.Owner;
import com.seg2505.project.model.Provider;
import com.seg2505.project.model.Service;


import java.util.ArrayList;
import java.util.List;

import android.widget.CompoundButton;
/**
 * Diedrick Ng
 * Kame House
 */
public class AvailAdapter extends RecyclerView.Adapter<AvailAdapter.MyViewHolder> {


    /**
     * The dataset/database used by the recycler view
     */

    private  List<Availability> mdata;
    private CompoundButton lastCheckedRB = null;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private RadioButton radioButton;
        private  TextView Day, Time;
        private CardView cardView;


        private MyViewHolder(View v) {
            super(v);
            cardView = v.findViewById(R.id.children);
            radioButton = v.findViewById(R.id.radioButton);
            Time = v.findViewById(R.id.time);
            Day = v.findViewById(R.id.day);


        }
    }




    public AvailAdapter(List<Availability> mdataset, Owner owner) {


       this.mdata = mdataset;


    }


    @Override

    public AvailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_service_provider, parent, false);
        return new AvailAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AvailAdapter.MyViewHolder holder, final int position) {

        holder.Day.setText("Day: " + mdata.get(position).getDay());
        holder.Time.setText("Time: " + mdata.get(position).getTime());
        holder.radioButton.setTag(position);
        holder.radioButton.setOnCheckedChangeListener(ls);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.radioButton.setOnCheckedChangeListener(ls);
            }
        });



    }

    private CompoundButton.OnCheckedChangeListener ls  = (new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int tag = (int) buttonView.getTag();
            ;
            if (lastCheckedRB == null) {
                lastCheckedRB = buttonView;
            } else if (tag != (int) lastCheckedRB.getTag()) {
                lastCheckedRB.setChecked(false);
                lastCheckedRB = buttonView;
            }

        }
    });


    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public List<Availability> getMdata() {
        return mdata;
    }


    public CompoundButton getLastCheckedRB() {
        return lastCheckedRB;
    }
}
