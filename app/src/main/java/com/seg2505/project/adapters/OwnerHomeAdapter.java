package com.seg2505.project.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.seg2505.project.R;
import com.seg2505.project.model.Provider;
import com.seg2505.project.model.Service;

import java.util.ArrayList;

public class OwnerHomeAdapter  extends RecyclerView.Adapter<OwnerHomeAdapter.MyViewHolder> {

    private ArrayList<OwnerHelper> dataset;
    private FirebaseDatabase database;

    public OwnerHomeAdapter () {
        dataset = new ArrayList<OwnerHelper>();

        database = FirebaseDatabase.getInstance();

        final DatabaseReference serviceReference = database.getReference("services");
        Query providerQuery  = database.getReference("users").orderByChild("role").equalTo("Provider");

        providerQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Provider provider = snapshot.getValue(Provider.class);
                        final OwnerHelper helper = new OwnerHelper();

                        helper.setProviderName(provider.getUsername());
                        helper.setProviderRating(provider.getRating());
                        helper.setWeekdays(provider.getAvailabilities());

                        for (String serviceID : provider.getServices()) {
                            serviceReference.child(serviceID).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Service service = dataSnapshot.getValue(Service.class);
                                    helper.setServiceName(service.getServiceName());
                                    dataset.add(helper);
                                    notifyItemInserted(dataset.size()-1);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static class MyViewHolder  extends RecyclerView.ViewHolder {

        private TextView serviceName, providerRating, weekdays, providerName;
        private ImageView bookingIcon;

        public MyViewHolder (View v) {
            super(v);

            serviceName = v.findViewById(R.id.serviceName);
            providerRating = v.findViewById(R.id.providerRating);
            providerName = v.findViewById(R.id.providerName);
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
    public void onBindViewHolder(@NonNull MyViewHolder vHolder, int i) {
        OwnerHelper current = dataset.get(i);

        vHolder.providerRating.setText(current.getProviderRating());
        vHolder.providerName.setText(current.getProviderName());
        vHolder.serviceName.setText(current.getServiceName());
        vHolder.weekdays.setText(current.getWeekdays());

        if (current.isBooked()) {
            vHolder.bookingIcon.setImageResource(R.drawable.ic_bookmark_booked_24dp);
        } else {
            vHolder.bookingIcon.setImageResource(R.drawable.ic_bookmark_unbooked_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
