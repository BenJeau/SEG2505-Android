package com.seg2505.project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seg2505.project.interfaces.Timeable;



public class CustomFragmentDay extends Fragment implements View.OnClickListener {
    private TextView edtDay;
    private TextView monday,tuesday,Wednesday,thursday,friday,saturaday,sunday;
    private String dayPicked;
    public static Timeable timeable;



    public static CustomFragmentDay createInstance()
    {
        CustomFragmentDay fragment = new CustomFragmentDay();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_day,container,false);
        edtDay = v.findViewById(R.id.day);
        monday = v.findViewById(R.id.mon);tuesday = v.findViewById(R.id.tue);
        Wednesday = v.findViewById(R.id.wed);thursday = v.findViewById(R.id.thu);
        friday = v.findViewById(R.id.fri);saturaday = v.findViewById(R.id.sat);
        sunday = v.findViewById(R.id.sun);
        monday.setOnClickListener(this);
        tuesday.setOnClickListener(this);
        Wednesday.setOnClickListener(this);
        thursday.setOnClickListener(this);
        friday.setOnClickListener(this);
        saturaday.setOnClickListener(this);
        sunday.setOnClickListener(this);
        return v;
    }
    public void setTimeable(Timeable timeable) {
        this.timeable = timeable;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.mon:
                dayPicked="Monday";
                break;
            case R.id.tue:
                dayPicked="Tuesday";
                break;
            case R.id.wed:
                dayPicked="Wednesday";
                break;
            case R.id.thu:
                dayPicked="Thursday";
                break;
            case R.id.fri:
                dayPicked="Friday";
                break;
            case R.id.sat:
                dayPicked="Saturday";
                break;
            case R.id.sun:
                dayPicked="Sunday";
                break;
                default:
                    break;
        }
        timeable.setDay(dayPicked);
        edtDay.setText(dayPicked);

        }

}
