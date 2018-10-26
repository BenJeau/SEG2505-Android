package com.seg2505.project;

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
    public Owner(String email, String password) {

        super(email, password);
        this.role = "Owner";
        services = new ArrayList<Service>();
    }

}
