package com.seg2505.project.model;

public class Booking {
    private String startTime;
    private String endTime;

    public Booking() {

    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
