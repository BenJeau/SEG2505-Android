package com.seg2505.project.model;

import java.util.*;
/**
 * Diedrick Ng
 * Kame House
 */
public class Owner extends Person {

    //Owner Associations
    private List<Booking> bookings;
    public Owner() {
        bookings = new ArrayList<>();
    }

    // CONSTRUCTOR
    public Owner(String username, String password,String id) {
        super(username, password,id);
        setRole("Owner");
        bookings = new ArrayList<>();
    }

    public void addBookings(Booking bookings) {
        this.bookings.add(bookings);
    }

    public List<Booking> getBookings() {
        return bookings;
    }
}
