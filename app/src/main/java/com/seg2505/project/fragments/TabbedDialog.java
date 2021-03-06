package com.seg2505.project.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seg2505.project.R;
import com.seg2505.project.interfaces.Cancelable;
import com.seg2505.project.interfaces.Timeable;
import com.seg2505.project.model.Availability;
import com.seg2505.project.model.LoggedUser;
import com.seg2505.project.model.Provider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class TabbedDialog extends DialogFragment implements Timeable {
    Button cancel,validate;
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView timeAvailability;
    String starttext,endText;
    int start,end;
    boolean modified;
    String day;
    Boolean canceled=false;
    public static Cancelable cancelable;
    private int hourCurrent,minuteCurrent;
    FirebaseDatabase database;
    DatabaseReference userReference;
    private String userId;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.dialog_sample,container,false);


        tabLayout =  rootview.findViewById(R.id.tabLayout);
        viewPager =  rootview.findViewById(R.id.masterViewPager);
        timeAvailability = rootview.findViewById(R.id.timeAvailability);
        cancel=rootview.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canceled=true;
                cancelable.setCanceled(canceled,"","");

            }
        });
        userId=LoggedUser.id;
        if(modified){
            timeAvailability.setText(day+" from "+starttext+" to "+endText);

        }
        else{
            start=Calendar.getInstance().get(Calendar.HOUR_OF_DAY)*60+Calendar.getInstance().get(Calendar.MINUTE);
            end=Calendar.getInstance().get(Calendar.HOUR_OF_DAY)*60+Calendar.getInstance().get(Calendar.MINUTE);
        }




        // Gets the information from firebase
        final ArrayList<String> data = new ArrayList<String>();
        database = FirebaseDatabase.getInstance();
        userReference = database.getReference().child("users").child(userId);
        validate=rootview.findViewById(R.id.validate);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Provider provider = dataSnapshot.getValue(Provider.class);
                if(provider.getAvailabilities()==null){
                    provider.createAvailabilities();
                }
                for (int i =0;i<provider.getAvailabilities().size();i++){
                    data.add(provider.getAvailabilities().get(i).getDay());
                }
                validate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.i("hhhh",start+"  "+end);
                        if(end==0 || start==0){
                            end=start=hourCurrent*60+minuteCurrent;
                        }
                        if(end-start>30){
                            if(modified){
                                Log.i("hhhh","kkkk");

                                canceled=false;
                                cancelable.setCanceled(canceled,day,starttext+" to "+endText);
                            }
                            else{
                                if (data.contains(day) ){
                                    Toast.makeText(getContext(), "Day has already been picked", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    canceled=false;
                                    cancelable.setCanceled(canceled,day,starttext+" to "+endText);
                                }

                            }



                        }

                        if(day==null){
                            Toast.makeText(getContext(), "Please choose a day", Toast.LENGTH_SHORT).show();

                        }
                        else if(end-start<30){

                            if (data.contains(day) && !modified){
                                Toast.makeText(getContext(), "Day has already been picked", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(getContext(), "Time slot must be greater than 30 minutes", Toast.LENGTH_SHORT).show();
                            }

                        }


                    }
                });



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });




        CustomFragmentDay day = CustomFragmentDay.createInstance();
        CustomFragmentTime startTime = CustomFragmentTime.createInstance(true);
        CustomFragmentTime endTime = CustomFragmentTime.createInstance(false);

        startTime.setTimeable(this);
        day.setTimeable(this);


        CustomFragmentAdapter adapter = new CustomFragmentAdapter(getChildFragmentManager());
        adapter.addFragment("Day",day);
        adapter.addFragment("Start time",startTime);
        adapter.addFragment("End time",endTime);


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
         hourCurrent = calendar.get(Calendar.HOUR_OF_DAY);
         minuteCurrent = calendar.get(Calendar.MINUTE);

        return rootview;
    }

    @Override
    public void setStartTime(int hour, int minute) {
        Log.i("ggh",hour+"");
        start=hour*60+minute;
        starttext=String.format("%02d:%02d", hour, minute);
        if (day==null && endText==null){
            timeAvailability.setText(" from "+starttext);
        }
        else {
            if (starttext==null){


                starttext=hourCurrent+":"+minuteCurrent;
            }
            if (endText==null){
                if (minuteCurrent<10){
                    endText=hourCurrent+":0"+minuteCurrent;
                }
                else{
                    endText=hourCurrent+":"+minuteCurrent;

                }

            }
            if (day==null){
                day=" ";
            }
            timeAvailability.setText(day+" from "+starttext+" to "+endText);
        }


    }

    @Override
    public void setEndTime(int hour, int minute) {
        Log.i("ggh",minute+"");

        end=hour*60+minute;
        endText=String.format("%02d:%02d", hour, minute);
        if (day==null && starttext==null){
            timeAvailability.setText(" to "+endText);
        }
        else {
            if (starttext==null){

                starttext=hourCurrent+":"+minuteCurrent;
            }
            if (endText==null){

                endText=hourCurrent+":"+minuteCurrent;

            }
            if (day==null){
                day=" ";

            }
            timeAvailability.setText(day+" from "+starttext+" to "+endText);
        }

    }

    @Override
    public void setDay(String dayPicked) {
        day=dayPicked;
        if (starttext==null && endText==null){
            timeAvailability.setText(day);
        }
        else{
            if (starttext==null){
                starttext=" ";
            }
            if (endText==null){
                endText=" ";
            }
            timeAvailability.setText(day+" from "+starttext+" to "+endText);

        }

    }

    @Override
    public void modified(Boolean modified, Availability availability) {
        this.modified=modified;
        this.day=availability.getDay();
        Log.i("kkk",availability.getTime()+"ll");

        starttext=availability.getTime().substring(0,5);
        endText=availability.getTime().substring(9,14);
        start=Integer.valueOf(starttext.substring(0,2))*60+Integer.valueOf(starttext.substring(3,5));
        end=Integer.valueOf(endText.substring(0,2))*60+Integer.valueOf(endText.substring(3,5));

        Log.i("kkk",starttext+"   "+endText);
        Log.i("kkk",start+"   "+end);
        Log.i("kkk",endText.substring(0,2)+"  "+endText.substring(3,5));


    }

    public void setCancelable(Cancelable cancelable) {
        this.cancelable = cancelable;
    }


}
