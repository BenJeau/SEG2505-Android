package com.seg2505.project.model;

import java.text.DecimalFormat;
import java.util.*;
/**
 * Diedrick Ng
 * Kame House
 */
public class Provider extends Person {
    //Provider Associations
    private List<String> services;
    private List<Availability> availabilities;
    private Address address;
    private ProviderInfo info;
    private List<Double> ratings;
    private String comment;

    public Provider() {
        services = new ArrayList<String>();
        availabilities = new ArrayList<Availability>();
        ratings = new ArrayList<Double>();
    }

    public Provider(String username, String password, String id) {
        super(username, password,id);
        setRole("Provider");
        services = new ArrayList<String>();
        availabilities = new ArrayList<Availability>();
        ratings = new ArrayList<Double>();
    }

    public void addAvailabity( Availability a){
        availabilities.add(a);
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setInfo( ProviderInfo info){
        this.info = info;
    }

    public ProviderInfo getInfo() {
        return this.info;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return this.address;
    }

    public void addService(String service) {
        services.add(service);
    }

    public void removeService(int position) {
        services.remove(position);
    }

    public List<String> getServices() {
        return services;
    }

    public void createServices(){
        services = new ArrayList<String>();
    }

    public void createAvailabilities(){
        availabilities = new ArrayList<Availability>();
    }

    public void setRatings(List<Double> ratings) {
        ratings = ratings;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return this.comment;
    }

    public List<Double> createRatings(){
        return new ArrayList<Double>();
    }

//    public String getRating() {
//        if (ratings.size() == 0) {
//            return "N.A.";
//        }
//
//        Double rating = 0.0;
//        for (Double rate : ratings) {
//            rating += rate;
//        }
//        DecimalFormat df = new DecimalFormat("#.##");
//
//        return df.format(rating);
//    }
}
