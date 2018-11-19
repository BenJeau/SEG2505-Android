package com.seg2505.project.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.seg2505.project.R;
import com.seg2505.project.model.Service;
import android.view.LayoutInflater;

import java.util.ArrayList;

/**
 * Diedrick Ng
 * Kame House
 */
public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.MyViewHolder> {


    /**
     * The dataset/database used by the recycler view
     */
    private ArrayList<Service> dataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView serviceTitle, hourlyRate, listPeople;
        private Button AddButton;


        private MyViewHolder(View v) {
            super(v);


            serviceTitle = v.findViewById(R.id.serviceTitle);
            hourlyRate = v.findViewById(R.id.serviceHourly);

        }
    }

    public DialogAdapter(ArrayList<Service> dataset) {
        this.dataset = dataset;

    }


    @Override
    public DialogAdapter.MyViewHolder onCreateViewHolder( ViewGroup parent, int i) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_services, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int i) {
        holder.serviceTitle.setText(dataset.get(i).getServiceName());
        holder.hourlyRate.setText(Double.toString(dataset.get(i).getHourlyRate()));

        

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
