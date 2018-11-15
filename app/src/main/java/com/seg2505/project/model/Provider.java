package com.seg2505.project.model;

import java.util.*;
/**
 * Diedrick Ng
 * Kame House
 */
public class Provider extends Person {
    //Provider Associations
    private List<Service> services;
    private List<Availability> availabilities;
    private Address address;

    public Provider() {

    }

    public Provider(String username, String password) {
        super(username, password);
        setRole("Provider");
        services = new ArrayList<Service>();
        availabilities = new ArrayList<Availability>();
    }

    public void addAvailabity( Availability a){
        availabilities.add(a);
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
