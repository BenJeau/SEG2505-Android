package com.seg2505.project.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.Iterator;

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

    private  ArrayList<Service> mdata;

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
    public DialogAdapter(ArrayList<Service> mdataset, Provider provider) {

        this.provider = provider;
        this.mdata = new ArrayList<>();

        // Enleve service deja selectionner

        Iterator<Service> i = provider.getServices().iterator();
        while(i.hasNext()){
            Service s = i.next();
            if(mdataset.contains(s)){
                mdataset.remove(s);
            }
        }
        this.dataset = mdataset;


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

        mdata.add(dataset.get(position));
        dataset.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dataset.size());
    }

    public ArrayList<Service> getDataset() {
        return dataset;
    }

    public ArrayList<Service> getMdata() {
        return mdata;
    }
}
