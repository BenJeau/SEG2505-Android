package com.seg2505.project.adapters;
import android.content.Context;
import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.seg2505.project.R;
import com.seg2505.project.fragments.TabbedDialog;
import com.seg2505.project.activities.ProviderAvailabilityActivity;
import com.seg2505.project.interfaces.Cancelable;
import com.seg2505.project.model.Availability;

import java.util.ArrayList;

public class AvailabilityAdapter extends RecyclerView.Adapter<AvailabilityAdapter.MyViewHolder> implements Cancelable {

    private TabbedDialog dialogFragment;


    /**
     * The dataset/database used by the recycler view
     */
    private ArrayList<Availability> dataset;

    /**
     * The context of the Activity
     */
    private Context context;

    /**
     * The layout to be displayed when the recycler view is empty
     */
    private ConstraintLayout emptyLayout;

    private static int position;
    private String timeText,dayText;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView day, time;
        private CardView cardView;
        private ImageView dropArrow;
        private Button deleteButton, modifyButton;
        private ConstraintLayout content;

        private MyViewHolder(View v) {
            super(v);

            day = v.findViewById(R.id.day);
            time = v.findViewById(R.id.time);
            cardView = v.findViewById(R.id.cardView);
            dropArrow = v.findViewById(R.id.dropArrow);
            deleteButton = v.findViewById(R.id.deleteButton);
            modifyButton = v.findViewById(R.id.modifyButton);
            content = v.findViewById(R.id.content);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AvailabilityAdapter(ArrayList<Availability> dataset, Context context) {
        this.dataset = dataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AvailabilityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.provider_availability_recycler_view_children, parent, false);
        return new AvailabilityAdapter.MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        this.position=position;
         timeText = dataset.get(position).getTime();
         dayText = dataset.get(position).getDay();

        holder.day.setText(dayText);
        holder.time.setText(timeText );

        // Initial setup (fixes bug when adding new service)
        holder.dropArrow.setRotation(0);
        holder.content.setVisibility(View.GONE);



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

        deleteDialogBuilder.setTitle("Delete Availability");
        deleteDialogBuilder.setMessage("Are you sure you want to delete the availability on '" + dataset.get(position).getDay() + "'");

        deleteDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                holder.content.setVisibility(View.GONE);
                removeAt(position);
                // TODO : Delete availability from firebase database
            }
        });

        deleteDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        final AlertDialog deleteDialog = deleteDialogBuilder.create();




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

                onCreateDialog1(position);
            }
        });
    }

    /**
     * Remove an availability from the dataset and recycler view while
     *
     * @param position the position of the availability in the list
     */
    public void removeAt(int position) {
        dataset.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dataset.size());
        ProviderAvailabilityActivity.userReference.child("availabilities").child(String.valueOf(position)).removeValue();


    }



    /**
     * Modifies the availability at the specified position and
     * refreshes the recycler view for that position
     *
     * @param availability the replacement availability object
     * @param position the position of the service object in the dataset
     */
    public void modifyAt(Availability availability, int position) {

        ProviderAvailabilityActivity.userReference.child("availabilities").child(String.valueOf(position)).setValue(availability);
        dataset.set(position, availability);
        notifyItemChanged(position);
    }

    /**
     * Adds the specified availability while refreshing the recycler view
     *
     * @param availability the availability object to be added
     */
    public void add(Availability availability) {
        dataset.add(availability);
        notifyItemInserted(dataset.size() - 1);

    }




    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }



    public void onCreateDialog1(int position) {
        FragmentTransaction ft =     ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        Fragment prev =     ((AppCompatActivity) context).getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        dialogFragment = new TabbedDialog();
        dialogFragment.show(ft,"dialog");
        dialogFragment.setCancelable(this);
        Log.i("kkk",position+"");
        dialogFragment.modified(true,dataset.get(position));


    }

    @Override
    public void setCanceled(Boolean canceled, String day, String time) {
        if (canceled){
            Log.i("tagged","dddddd");

        }
        else{
            final Availability availability = new Availability(day, time);
            modifyAt(availability,position);

        }
        dialogFragment.dismiss();

    }
}
