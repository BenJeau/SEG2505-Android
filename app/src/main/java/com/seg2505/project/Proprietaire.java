package com.seg2505.project;

import java.util.*;
/**
 * Diedrick Ng
 * Kame House
 */
public class Proprietaire extends Person {

    //Proprietaire Associations

    private List<Service> services;
    public Proprietaire() {

    }


    // CONSTRUCTOR
    public Proprietaire(String email, String password) {

        super(email, password);
        this.role = "User";
        services = new ArrayList<Service>();
    }

}
