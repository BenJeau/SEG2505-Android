package com.seg2505.project.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seg2505.project.R;
import com.seg2505.project.model.Service;

import java.util.ArrayList;

public class OwnerHomeAdapter  extends RecyclerView.Adapter<OwnerHomeAdapter.MyViewHolder> {

    private ArrayList<Service> dataset;

    public OwnerHomeAdapter (ArrayList<Service> dataset) {
        this.dataset = dataset;
    }

    public static class MyViewHolder  extends RecyclerView.ViewHolder {

        private TextView serviceName, serviceRating, weekdays;
        private ImageView bookingIcon;

        public MyViewHolder (View v) {
            super(v);

            serviceName = v.findViewById(R.id.serviceName);
            serviceRating = v.findViewById(R.id.serviceRating);
            weekdays = v.findViewById(R.id.weekdays);
            bookingIcon = v.findViewById(R.id.booking);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.owner_service_recycler_view_children, viewGroup, false);
        return new OwnerHomeAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Service currentService = dataset.get(i);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
