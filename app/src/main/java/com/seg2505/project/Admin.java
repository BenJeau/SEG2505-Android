package com.seg2505.project;

import java.util.*;
/**
 * Diedrick Ng
 * Kame House
 */
public class Admin extends Person {

    private List<Service> services;

    public Admin(String email, String password){
        super(email, password);
        services = new ArrayList<Service>();
    }


}
