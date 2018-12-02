package com.seg2505.project.model;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Diedrick Ng
 * Kame House
 */
public class Availability {

    //Availability Attributes
    private String day;
    private String time;

    private ArrayList<String> Ownerid;
    private ArrayList<String> bookedDate;

    // CONSTRUCTOR
    public Availability(){

    }

    public Availability(String aDay, String aTime) {
        Ownerid = new ArrayList<>();
        bookedDate = new ArrayList<>();

        day = aDay;
        time = aTime;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String toString() {
        return day + " " + time;
    }

    public void add(String ownerid, String Date){
       Ownerid.add(ownerid);
       bookedDate.add(Date);
    }

    public ArrayList<String> getBookedDate() {
        return bookedDate;
    }

    public ArrayList<String> getOwnerid() {
        return Ownerid;
    }
}
