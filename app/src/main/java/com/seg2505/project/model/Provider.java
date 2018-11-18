package com.seg2505.project.model;

import java.util.*;
/**
 * Diedrick Ng
 * Kame House
 */
public class Provider extends Person {
    //Provider Associations
    private List<Service> services;
    private List<Availability> availabilities;
    private Address address;

    public Provider() {

    }

    public Provider(String username, String password,String id) {
        super(username, password,id);
        setRole("Provider");
        services = new ArrayList<Service>();
        availabilities = new ArrayList<Availability>();
    }

    public void addAvailabity( Availability a){
        availabilities.add(a);
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return this.address;
    }

    public void addService(Service service) {
        services.add(service);
    }

    public void removeService(int position) {
        services.remove(position);
    }

    public List<Service> getServices() {
        return services;
    }

    public void createServices(){
        services = new ArrayList<Service>();
    }

    public void createAvailabilities(){
        availabilities = new ArrayList<Availability>();
    }
}
