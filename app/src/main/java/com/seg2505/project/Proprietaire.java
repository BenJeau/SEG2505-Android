package com.seg2505.project;

import java.util.*;
/**
 * Diedrick Ng
 * Kame House
 */
public class Proprietaire extends Person {

    //Proprietaire Associations

    private List<Service> services;


    // CONSTRUCTOR
    public Proprietaire(String aName, String aLastName) {

        super(aName, aLastName);
        services = new ArrayList<Service>();
    }

}
