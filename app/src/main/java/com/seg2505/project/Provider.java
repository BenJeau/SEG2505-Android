package com.seg2505.project;

import java.util.*;
/**
 * Diedrick Ng
 * Kame House
 */
public class Provider extends Person {
    //Provider Associations
    private List<Service> services;
    private List<Availability> availabilities;

    public Provider() {

    }
    public Provider(String email, String password) {
        super(email, password);
        this.role= "Provider";
        services = new ArrayList<Service>();
        availabilities = new ArrayList<Availability>();
    }
}
