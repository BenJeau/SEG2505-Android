package com.seg2505.project.interfaces;

import com.seg2505.project.model.Availability;

public interface Timeable {
    public void setStartTime(int hour, int minute);

    public void setEndTime(int hour, int minute);

    public void setDay(String dayPicked);

    public void modified(Boolean modified, Availability availability);

}
