package com.seg2505.project;

import java.util.*;
/**
 * Diedrick Ng
 * Kame House
 */
public class Fournisseur extends Person {
    //Fournisseur Associations
    private List<Service> services;
    private List<Disponibilite> disponibilites;

    public Fournisseur() {

    }
    public Fournisseur(String email, String password) {
        super(email, password);
        services = new ArrayList<Service>();
        disponibilites = new ArrayList<Disponibilite>();
    }
}
