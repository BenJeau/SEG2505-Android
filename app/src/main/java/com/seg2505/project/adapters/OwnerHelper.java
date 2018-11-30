package com.seg2505.project.adapters;

import com.seg2505.project.model.Availability;

import java.util.ArrayList;
import java.util.List;

public class OwnerHelper {
    private String serviceName;
    private String providerName;
    private String providerRating;
    private boolean booked;
    private String weekdays;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderRating() {
        return providerRating;
    }

    public void setProviderRating(String providerRating) {
        this.providerRating = providerRating;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public String getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(List<Availability> availabilities) {
        weekdays = "";

        for (Availability availability : availabilities) {
            weekdays += availability.getDay().substring(0, 3).toUpperCase() + ", ";
        }

        if (!weekdays.equals("")) {
            weekdays = weekdays.substring(0, weekdays.length() - 2);
        }
    }
}
