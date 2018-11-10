package com.seg2505.project.model;

import java.util.*;
/**
 * Diedrick Ng
 * Kame House
 */
public class Owner extends Person {

    //Owner Associations
    private List<Service> services;
    public Owner() {

    }

    // CONSTRUCTOR
    public Owner(String username, String password) {
        super(username, password);
        setRole("Owner");
        services = new ArrayList<Service>();
    }
}
