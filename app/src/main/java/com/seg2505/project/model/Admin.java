package com.seg2505.project.model;

import java.util.*;

public class Admin extends Person {

    private List<Service> services;
    public Admin(){

    }

    public Admin(String username, String password,String id){
        super(username, password, id);
        services = new ArrayList<Service>();
    }
    public void addService(Service s){
        services.add(s);
    }
}
