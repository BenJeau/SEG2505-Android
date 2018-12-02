package com.seg2505.project.model;

public class Booking {
    private String idProvider, date;
    private int index;

    public Booking(String idProvider, int index, String date) {
        this.idProvider = idProvider;
        this.index = index;
        this.date = date;

    }

    public String getIdProvider() {
        return idProvider;
    }

    public int getIndex() {
        return index;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setIdProvider(String idProvider) {
        this.idProvider = idProvider;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
