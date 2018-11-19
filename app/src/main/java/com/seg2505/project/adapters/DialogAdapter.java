package com.seg2505.project.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.seg2505.project.R;
import com.seg2505.project.model.LoggedUser;
import com.seg2505.project.model.Provider;
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

    private Provider provider;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView serviceTitle, hourlyRate, listPeople;
        public ImageView addIcon;


        private MyViewHolder(View v) {
            super(v);


            serviceTitle = v.findViewById(R.id.serviceTitle);
            hourlyRate = v.findViewById(R.id.serviceHourly);
            addIcon = v.findViewById(R.id.AddIcon);

        }
    }

    public DialogAdapter(ArrayList<Service> dataset, Provider provider) {
        this.dataset = dataset;
        this.provider = provider;

    }


    @Override
    public DialogAdapter.MyViewHolder onCreateViewHolder( ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_service_recyler_view, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, final int position) {

        holder.serviceTitle.setText(dataset.get(position).getServiceName());
        holder.hourlyRate.setText(Double.toString(dataset.get(position).getHourlyRate()));
        holder.addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void add(int position){

        Service service = dataset.get(position);
        service.addProvider(provider);
        // TODO : Integrate Firebase  here
        
//        String userId = LoggedUser.id;
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference serviceReference =  database.getReference().child("users").child(userId);
//        ProviderRecyclerViewAdapter adapter = new ProviderRecyclerViewAdapter(dataset);
//
//        String id = serviceReference.push().getKey();
//        serviceReference.child(id).setValue(service);
//        adapter.add(service);

        dataset.remove(position);


    }

}
