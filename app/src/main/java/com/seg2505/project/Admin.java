package com.seg2505.project;

import java.util.*;
/**
 * Diedrick Ng
 * Kame House
 */
public class Admin extends Person {

    private List<Service> services;
    public Admin(){

    }

    public Admin(String username, String password){
        super(username, password);
        services = new ArrayList<Service>();
    }
    public void addService(Service s){
        services.add(s);
    }
}
