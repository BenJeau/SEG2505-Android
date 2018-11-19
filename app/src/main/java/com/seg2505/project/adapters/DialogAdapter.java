package com.seg2505.project.adapters;


import android.content.Context;
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

    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView serviceTitle, hourlyRate, listPeople;
        public ImageView deleteIcon;


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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_service_recyler_view, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int i) {
        holder.serviceTitle.setText(dataset.get(i).getServiceName());
        holder.hourlyRate.setText(Double.toString(dataset.get(i).getHourlyRate()));
        

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

}
