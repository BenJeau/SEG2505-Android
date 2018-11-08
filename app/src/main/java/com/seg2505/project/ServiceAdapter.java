package com.seg2505.project;

import android.content.Context;
import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {
    private ArrayList<Service> mDataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView serviceTitle, hourlyCost, listPeople;
        public CardView cardView;
        public ImageView dropArrow;
        public Button deleteButton, modifyButton;
        public ConstraintLayout content;

        public MyViewHolder(View v) {
            super(v);

            serviceTitle = v.findViewById(R.id.serviceTitle);
            hourlyCost = v.findViewById(R.id.hourlyRate);
            listPeople = v.findViewById(R.id.listPeople);
            cardView = v.findViewById(R.id.cardView);
            dropArrow = v.findViewById(R.id.dropArrow);
            deleteButton = v.findViewById(R.id.deleteButton);
            modifyButton = v.findViewById(R.id.modifyButton);
            content = v.findViewById(R.id.content);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ServiceAdapter(ArrayList<Service> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
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
    public void onBindViewHolder(final ServiceAdapter.MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.serviceTitle.setText(mDataset.get(position).getServiceName());
        holder.hourlyCost.setText(Double.toString(mDataset.get(position).getHourlyRate()) + " $");

        // Get list of people
        StringBuilder listOfPeople = new StringBuilder();
        List<Provider> providers = mDataset.get(position).getProviders();
        for (int i = 0; i < providers.size(); i++) {
            listOfPeople.append(providers.get(i).getUsername());
            if (i != providers.size() - 1) {
                listOfPeople.append("\n");
            }
        }
        holder.listPeople.setText(listOfPeople);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.dropArrow.getRotation() == 0) {
                    holder.dropArrow.setRotation(180);
                    holder.content.setVisibility(View.VISIBLE);
                } else {
                    holder.dropArrow.setRotation(0);
                    holder.content.setVisibility(View.GONE);
                }
            }
        });

        AlertDialog.Builder deleteDialogBuilder = new AlertDialog.Builder(context);
        deleteDialogBuilder.setMessage("Are you sure you want to delete the service named '" + mDataset.get(position).getServiceName() + "'")
                .setTitle("Delete Service");
        deleteDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                removeAt(position);
                // TODO : Delete service from firebase database
            }
        });
        deleteDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        final AlertDialog deleteDialog = deleteDialogBuilder.create();

        AlertDialog.Builder modifyDialogBuilder = new AlertDialog.Builder(context);
        modifyDialogBuilder.setMessage("Are you sure you want to modify the service named '" + mDataset.get(position).getServiceName() + "'")
                .setTitle("Modify Service");
        modifyDialogBuilder.setPositiveButton("Modify", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // TODO : Modify service from firebase database
            }
        });
        modifyDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        final AlertDialog modifyDialog = modifyDialogBuilder.create();

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.show();
            }
        });
        holder.modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyDialog.show();
            }
        });
    }

    public void removeAt(int position) {
        mDataset.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDataset.size());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
