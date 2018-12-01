package com.seg2505.project.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.seg2505.project.R;
import com.seg2505.project.interfaces.Timeable;



public class CustomFragmentTime extends Fragment {
    public  TimePicker timePicker;
    public static String time;
    public static Timeable timeable;
    public boolean state;

    public void setTimeable(Timeable timeable) {
        this.timeable = timeable;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static CustomFragmentTime createInstance(Boolean state)
    {
        final CustomFragmentTime fragment = new CustomFragmentTime();
        fragment.state=state;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_time_picker,container,false);
        timePicker = v.findViewById(R.id.timePicker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {


                if (state){
                    timeable.setStartTime(hourOfDay, minute);
                }
                else{
                    timeable.setEndTime(hourOfDay, minute);

                }

            }
        });
        return v;
    }


}
