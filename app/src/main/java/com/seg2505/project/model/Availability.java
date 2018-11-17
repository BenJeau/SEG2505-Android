package com.seg2505.project.model;

/**
 * Diedrick Ng
 * Kame House
 */
public class Availability {

    //Availability Attributes
    private String day;
    private String time;

    // CONSTRUCTOR
    public Availability(){

    }

    public Availability(String aDay, String aTime) {
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
}
