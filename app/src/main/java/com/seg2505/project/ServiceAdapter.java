package com.seg2505.project;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {
    private ArrayList<Service> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView serviceTitle, hourlyCost, listPeople;
        public CardView cardView;

        public MyViewHolder(View v) {
            super(v);

            serviceTitle = v.findViewById(R.id.serviceTitle);
            hourlyCost = v.findViewById(R.id.hourlyCost);
            listPeople = v.findViewById(R.id.listPeople);
            cardView = v.findViewById(R.id.cardView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ServiceAdapter(ArrayList<Service> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ServiceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_service_recycler_view_children, parent, false);
        return new ServiceAdapter.MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ServiceAdapter.MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.serviceTitle.setText(mDataset.get(position).getServiceName());
        holder.hourlyCost.setText(Double.toString(mDataset.get(position).getHourlyRate()) + " $");

        // Get list of people
        StringBuilder listOfPeople = new StringBuilder();
        List<Provider> providers = mDataset.get(position).getProviders();
        for (int i = 0; i < providers.size(); i++) {
            listOfPeople.append(providers.get(i).getEmail());
            if (i != providers.size() - 1) {
                listOfPeople.append("\n");
            }
        }
        holder.listPeople.setText(listOfPeople);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.listPeople.getVisibility() == View.GONE) {
                    holder.listPeople.setVisibility(View.VISIBLE);
                    holder.hourlyCost.setVisibility(View.VISIBLE);
                    Log.e("TESTEET", "1111111111");
                } else {
                    holder.listPeople.setVisibility(View.GONE);
                    holder.hourlyCost.setVisibility(View.GONE);
                    Log.e("TESTEET", "2222222222");
                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
