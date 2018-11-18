package com.seg2505.project;

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

import com.seg2505.project.interfaces.Cancelable;
import com.seg2505.project.interfaces.Timeable;


public class TabbedDialog extends DialogFragment implements Timeable {
    Button cancel,validate;
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView timeAvailability;
    String starttext,endText;
    String day;
    Boolean canceled=false;
    public static Cancelable cancelable;

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
        validate=rootview.findViewById(R.id.validate);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canceled=false;
                cancelable.setCanceled(canceled,day,starttext+" to "+endText);

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

        return rootview;
    }

    @Override
    public void setStartTime(int hour, int minute) {
        starttext=String.format("%02d:%02d", hour, minute);
        if (day==null && endText==null){
            timeAvailability.setText(" from "+starttext);
        }
        else {
            if (starttext==null){
                starttext=" ";
            }
            if (endText==null){
                endText=" ";
            }
            if (day==null){
                day=" ";
            }
            timeAvailability.setText(day+" from "+starttext+" to "+endText);
        }


    }

    @Override
    public void setEndTime(int hour, int minute) {
        endText=String.format("%02d:%02d", hour, minute);
        if (day==null && starttext==null){
            timeAvailability.setText(" to "+endText);
        }
        else {
            if (starttext==null){
                starttext=" ";
            }
            if (endText==null){
                endText=" ";
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
    public void setCancelable(Cancelable cancelable) {
        this.cancelable = cancelable;
    }

}
