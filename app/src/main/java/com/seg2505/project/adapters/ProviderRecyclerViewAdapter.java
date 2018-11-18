package com.seg2505.project.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seg2505.project.R;
import com.seg2505.project.model.Person;
import com.seg2505.project.model.Service;

import java.util.ArrayList;
import java.util.List;

public class ProviderRecyclerViewAdapter extends RecyclerView.Adapter<ProviderRecyclerViewAdapter.MyViewHolder> {
    private List<Service> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView serviceTitle, hourlyRate;
        public ImageView deleteIcon;

        public MyViewHolder(View v) {
            super(v);

            serviceTitle = v.findViewById(R.id.serviceTitle);
            hourlyRate = v.findViewById(R.id.serviceHourly);
            deleteIcon = v.findViewById(R.id.deleteIcon);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProviderRecyclerViewAdapter(List<Service> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProviderRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.provider_service_recycler_view_children, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.serviceTitle.setText(mDataset.get(position).getServiceName());
        holder.hourlyRate.setText(Double.toString(mDataset.get(position).getHourlyRate()));
        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(position);
            }
        });
    }

    /**
     * Remove a service from the dataset and recycler view while
     *
     * @param position the position of the service in the list
     */
    public void removeAt(int position) {
        mDataset.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDataset.size());
    }

    /**
     * Adds the specified service while refreshing the recycler view
     *
     * @param service the service object to be added
     */
    public void add(Service service) {
        mDataset.add(service);
        notifyItemInserted(mDataset.size() - 1);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
