package com.seg2505.project.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import com.seg2505.project.activities.BookingActivity;

import java.util.Calendar;

/**
 * Diedrick Ng
 * Kame House
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private String date;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        date = this.getArguments().getString("date");

        // Use the current date as the default date in the picker
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }


    public void onDateSet(DatePicker view, int year, int month, int day) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);

        boolean valid;

        System.out.println(c.get(Calendar.DAY_OF_WEEK));
        System.out.println(date.toLowerCase().contains("mon"));

        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case 2:
                valid = (date.toLowerCase().contains("mon"));
                break;
            case 3:
                valid = (date.toLowerCase().contains("tue"));
                break;
            case 4:
                valid = (date.toLowerCase().contains("wed"));
                break;
            case 5:
                valid = (date.toLowerCase().contains("thu"));
                break;
            case 6:
                valid = (date.toLowerCase().contains("fri"));
                break;
            case 7:
                valid = (date.toLowerCase().contains("sat"));
                break;
            case 1:
                valid = (date.toLowerCase().contains("sun"));
                break;
            default:
                valid = false;
        }

        if (!valid) {
            DatePickerFragment newfra = new DatePickerFragment();
            newfra.show(getFragmentManager(), "timePicker");

            Bundle bundle = new Bundle();
            bundle.putString("date", date);
            newfra.setArguments(bundle);

            Toast.makeText(getContext(), "Please select a date which is a " + date, Toast.LENGTH_LONG).show();
        } else {
            // Do something with the date chosen by the user
            ((BookingActivity)getActivity()).book(day +"/"+ month +"/"+ year);
        }
    }
}