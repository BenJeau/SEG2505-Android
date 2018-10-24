package com.seg2505.project;

import java.util.*;
/**
 * Diedrick Ng
 * Kame House
 */
public class Admin extends Person {

    private List<Service> services;

    public Admin(String aName, String aLastName){
        super(aName, aLastName);
        services = new ArrayList<Service>();
    }


}
