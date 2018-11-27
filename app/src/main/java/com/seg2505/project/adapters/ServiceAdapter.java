package com.seg2505.project.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seg2505.project.R;
import com.seg2505.project.activities.AdminServiceActivity;
import com.seg2505.project.model.Owner;
import com.seg2505.project.model.Person;
import com.seg2505.project.model.Provider;
import com.seg2505.project.model.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {

    /**
     * The dataset/database used by the recycler view
     */
    private ArrayList<Service> dataset;

    /**
     * The context of the Activity
     */
    private Context context;

    /**
     * The layout to be displayed when the recycler view is empty
     */
    private ConstraintLayout emptyLayout;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView serviceTitle, hourlyCost, listPeople;
        private CardView cardView;
        private ImageView dropArrow;
        private Button deleteButton, modifyButton;
        private ConstraintLayout content;

        private MyViewHolder(View v) {
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
    public ServiceAdapter(ArrayList<Service> dataset, Context context) {
        this.dataset = dataset;
        this.context = context;
        checkIfEmpty();
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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String hourlyRateText = Double.toString(dataset.get(position).getHourlyRate());
        String serviceNameText = dataset.get(position).getServiceName();

        holder.serviceTitle.setText(serviceNameText);
        holder.hourlyCost.setText(hourlyRateText + " $");

        // Initial setup (fixes bug when adding new service)
        holder.dropArrow.setRotation(0);
        holder.content.setVisibility(View.GONE);


        // Gets list of providers and concatenates it into a string
        DatabaseReference serviceReference = FirebaseDatabase.getInstance().getReference("services");
        serviceReference.child(dataset.get(position).getServiceId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Service service = dataSnapshot.getValue(Service.class);
                List<String> providers = service.getProviders();

                if (providers != null) {

                    DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference("users");
                    for (int i = 0; i < providers.size(); i++) {
                        usersReference.child(providers.get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Provider provider = dataSnapshot.getValue(Provider.class);
                                if (provider != null) {
                                    if (holder.listPeople.getText().length() == 0){
                                        holder.listPeople.setText(holder.listPeople.getText() + provider.getUsername());
                                    } else {
                                        holder.listPeople.setText(holder.listPeople.getText() + "\n" + provider.getUsername());
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        // Expends/Closes the CardView when clicked
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

        // Gets the layoutInflater from the context of the activity
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Creates the delete dialog
        AlertDialog.Builder deleteDialogBuilder = new AlertDialog.Builder(context);

        deleteDialogBuilder.setTitle("Delete Service");
        deleteDialogBuilder.setMessage("Are you sure you want to delete the service named '" + dataset.get(position).getServiceName() + "'");

        deleteDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                holder.content.setVisibility(View.GONE);
                removeAt(position);
                // TODO : Delete service from firebase database
            }
        });

        deleteDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        final AlertDialog deleteDialog = deleteDialogBuilder.create();

        // Creates the modify dialog
        AlertDialog.Builder modifyDialogBuilder = new AlertDialog.Builder(context);
        modifyDialogBuilder.setTitle("Modify Service");

        View view = layoutInflater.inflate(R.layout.create_service, null);
        final EditText hourlyRate = view.findViewById(R.id.hourlyRate);
        final EditText serviceName = view.findViewById(R.id.serviceName);

        hourlyRate.setText(Double.toString(dataset.get(position).getHourlyRate()));
        serviceName.setText(dataset.get(position).getServiceName());

        modifyDialogBuilder.setView(view);

        modifyDialogBuilder.setPositiveButton("Modify", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        modifyDialogBuilder.setNegativeButton("Cancel", null);
        final AlertDialog modifyDialog = modifyDialogBuilder.create();

        // The callback methods for the buttons in each CardView/children
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
                modifyDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String serviceNameText = serviceName.getText().toString();
                        String hourlyRateText = hourlyRate.getText().toString();

                        if (AdminServiceActivity.validateDialog(serviceName, hourlyRate, serviceNameText, hourlyRateText)) {
                            Service service = dataset.get(position);
                            service.setHourlyRate(Double.parseDouble(hourlyRateText));
                            service.setServiceName(serviceNameText);

                            modifyAt(service, position);

                            modifyDialog.dismiss();

                            // TODO : Modify service from firebase database
                        }
                    }
                });
            }
        });
    }

    /**
     * Remove a service from the dataset and recycler view while
     *
     * @param position the position of the service in the list
     */
    public void removeAt(final int position) {
        final String serviceId = dataset.get(position).getServiceId();

        AdminServiceActivity.serviceReference.child(serviceId).removeValue();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userReference = database.getReference("users");
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Person user = postSnapshot.getValue(Person.class);
                    if (user.getRole().equals(("Provider"))) {
                        Provider provider = postSnapshot.getValue(Provider.class);
                        if (provider.getServices() != null && provider.getServices().contains(serviceId)) {
                            provider.removeService(provider.getServices().indexOf(serviceId));
                            userReference.child(provider.getId()).setValue(provider);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        dataset.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dataset.size());

        checkIfEmpty();
    }


    /**
     * Modifies the service at the specified position and
     * refreshes the recycler view for that position
     *
     * @param service  the replacement service object
     * @param position the position of the service object in the dataset
     */
    public void modifyAt(Service service, int position) {
        AdminServiceActivity.serviceReference.child(dataset.get(position).getServiceId()).setValue(service);
        dataset.set(position, service);
        notifyItemChanged(position);
    }

    /**
     * Adds the specified service while refreshing the recycler view
     *
     * @param service the service object to be added
     */
    public void add(Service service) {
        dataset.add(service);
        notifyItemInserted(dataset.size() - 1);

        checkIfEmpty();
    }


    /**
     * Adds/remove view for when the recycler view is empty
     */
    private void checkIfEmpty() {
        if (emptyLayout == null) {
            emptyLayout = ((Activity) context).getWindow().getDecorView().findViewById(R.id.emptyLayout);
        }

        if (dataset.isEmpty()) {
            emptyLayout.setVisibility(View.VISIBLE);
        } else if (emptyLayout.getVisibility() != View.GONE) {
            emptyLayout.setVisibility(View.GONE);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
