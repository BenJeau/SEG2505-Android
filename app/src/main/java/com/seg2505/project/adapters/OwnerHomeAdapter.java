package com.seg2505.project.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.seg2505.project.R;
import com.seg2505.project.activities.BookingActivity;
import com.seg2505.project.activities.RatingProviderActivity;
import com.seg2505.project.model.Booking;
import com.seg2505.project.model.LoggedUser;
import com.seg2505.project.model.Owner;
import com.seg2505.project.model.Provider;
import com.seg2505.project.model.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OwnerHomeAdapter extends RecyclerView.Adapter<OwnerHomeAdapter.MyViewHolder> {

    private ArrayList<OwnerHelper> dataset;
    private FirebaseDatabase database;
    private Context context;

    private final SortedList<OwnerHelper> ownerHelperSortedList = new SortedList<>(OwnerHelper.class, new SortedList.Callback<OwnerHelper>() {
        @Override
        public int compare(OwnerHelper a, OwnerHelper b) {
            return a.toString().compareTo(b.toString());
        }

        @Override
        public void onInserted(int position, int count) {
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count) {
            notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(OwnerHelper oldItem, OwnerHelper newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(OwnerHelper item1, OwnerHelper item2) {
            return item1.toString().equals(item2.toString());
        }
    });

    // The following 5 functions are for the manipulation of the SortedList
    public void add(OwnerHelper oHelper) {
        ownerHelperSortedList.add(oHelper);
    }

    public void remove(OwnerHelper oHelper) {
        ownerHelperSortedList.remove(oHelper);
    }

    public void add(List<OwnerHelper> oHelpers) {
        for (OwnerHelper i : oHelpers) {
            add(i);
        }
    }

    public void remove(List<OwnerHelper> oHelpers) {
        ownerHelperSortedList.beginBatchedUpdates();
        for (OwnerHelper product : oHelpers) {
            ownerHelperSortedList.remove(product);
        }
        ownerHelperSortedList.endBatchedUpdates();
    }

    public void replaceAll(List<OwnerHelper> oHelpers) {
        ownerHelperSortedList.beginBatchedUpdates();
        for (int i = ownerHelperSortedList.size() - 1; i >= 0; i--) {
            OwnerHelper oHelper = ownerHelperSortedList.get(i);
            if (!oHelpers.contains(oHelper)) {
                int temp = ownerHelperSortedList.indexOf(oHelper);
                ownerHelperSortedList.remove(oHelper);

                // Updates the position of the children of RecyclerView when items are removed
                this.notifyItemRemoved(temp);
                this.notifyItemRangeChanged(temp, ownerHelperSortedList.size());
            }
        }
        ArrayList<OwnerHelper> pos1 = new ArrayList<>(oHelpers);
        ownerHelperSortedList.addAll(oHelpers);
        for (int i = 0; i < pos1.size(); i++) {
            // Updates the position of the children of RecyclerView when items are moved
            this.notifyItemMoved(i, ownerHelperSortedList.indexOf(pos1.get(i)));
            this.notifyItemRangeChanged(i, ownerHelperSortedList.size());
        }
        ownerHelperSortedList.endBatchedUpdates();
    }

    public void getData() {
        int size = ownerHelperSortedList.size();
        for (int i = 0; i < size; i++) {
            remove(ownerHelperSortedList.get(0));
        }

        dataset = new ArrayList<OwnerHelper>();

        database = FirebaseDatabase.getInstance();

        final DatabaseReference serviceReference = database.getReference("services");
        final Query providerQuery = database.getReference("users").orderByChild("role").equalTo("Provider");

        database.getReference("users").child(LoggedUser.id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    final Owner owner = dataSnapshot.getValue(Owner.class);

                    providerQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    final Provider provider = snapshot.getValue(Provider.class);
                                    final OwnerHelper helper = new OwnerHelper();

                                    helper.setProviderID(provider.getId());
                                    helper.setProviderName(provider.getUsername());

                                    if (provider.getRatings() == null || provider.getRatings().isEmpty()) {
                                        helper.setProviderRating("N.A.");
                                    } else {
                                        DecimalFormat df = new DecimalFormat("#.##");
                                        Double sum = 0.0;

                                        for (Double i : provider.getRatings()) {
                                            sum += i;
                                        }

                                        helper.setProviderRating(df.format(sum / provider.getRatings().size()));
                                    }

                                    helper.setWeekdays(provider.getAvailabilities());

                                    for (final String serviceID : provider.getServices()) {
                                        serviceReference.child(serviceID).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                Service service = dataSnapshot.getValue(Service.class);
                                                helper.setServiceName(service.getServiceName());
                                                helper.setServiceID(serviceID);

                                                helper.setBooked(false);

                                                if (owner.getBookings() != null) {
                                                    for (Booking booking : owner.getBookings()) {
                                                        String serviceid = booking.getServiceid();
                                                        String proverid = booking.getIdProvider();

                                                        if (serviceID.equals(serviceid) && provider.getId().equals(proverid)) {
                                                            helper.setBooked(true);
                                                            System.out.println("-------------------");
                                                            System.out.println(helper);
                                                            System.out.println(serviceID);
                                                            System.out.println(provider.getId());
                                                            break;
                                                        }

                                                    }
                                                }


                                                OwnerHelper copy = helper.copy();
                                                dataset.add(copy);
                                                ownerHelperSortedList.add(copy);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public OwnerHomeAdapter(Context context) {
        getData();
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView serviceName, providerRatingText, weekdays, providerName;
        private ImageView bookingIcon;
        private RatingBar providerRatingStars;
        private CardView cardView;

        private MyViewHolder(View v) {
            super(v);

            serviceName = v.findViewById(R.id.serviceName);
            providerRatingText = v.findViewById(R.id.providerRatingText);
            providerRatingStars = v.findViewById(R.id.providerRatingStars);
            providerName = v.findViewById(R.id.providerName);
            weekdays = v.findViewById(R.id.weekdays);
            bookingIcon = v.findViewById(R.id.booking);
            cardView = v.findViewById(R.id.cardViewLayout);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.owner_service_recycler_view_children, viewGroup, false);
        return new OwnerHomeAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder vHolder, int i) {
        final OwnerHelper current = ownerHelperSortedList.get(i);

        if (!current.getProviderRating().equals("N.A.")) {
            vHolder.providerRatingStars.setVisibility(View.VISIBLE);
            vHolder.providerRatingStars.setRating(Float.parseFloat(current.getProviderRating()));
            vHolder.providerRatingText.setVisibility(View.GONE);
        } else {
            vHolder.providerRatingStars.setVisibility(View.GONE);
            vHolder.providerRatingText.setVisibility(View.VISIBLE);
        }

        vHolder.providerName.setText(current.getProviderName());
        vHolder.serviceName.setText(current.getServiceName());
        vHolder.weekdays.setText(current.getWeekdays());

        if (current.isBooked()) {
            vHolder.bookingIcon.setImageResource(R.drawable.ic_bookmark_booked_24dp);
        } else {
            vHolder.bookingIcon.setImageResource(R.drawable.ic_bookmark_unbooked_24dp);
        }

        vHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (current.isBooked()) {
                    intent = new Intent(context, RatingProviderActivity.class);
                } else {
                    intent = new Intent(context, BookingActivity.class);
                }
                intent.putExtra(INTENT_PROVIDER, current.getProviderID());
                intent.putExtra(INTENT_SERVICE, current.getServiceID());
                context.startActivity(intent);
            }
        });
    }

    public static final String INTENT_SERVICE = "SERVICE_ID";
    public static final String INTENT_PROVIDER = "PROVIDER_ID";

    @Override
    public int getItemCount() {
        return ownerHelperSortedList.size();
    }

    public ArrayList<OwnerHelper> getDataset() {
        return dataset;
    }
}
