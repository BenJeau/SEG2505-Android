package com.seg2505.project.model;

import com.seg2505.project.model.Provider;

import java.util.*;
/**
 * Diedrick Ng
 * Kame House
 */
public class Service {

    //Service Associations
    private List<Provider> providers;
    private String serviceName;
    private double hourlyRate;

    // CONSTRUCTOR
    public Service(String serviceName, double hourlyRate){
        providers = new ArrayList<Provider>();

        this.serviceName = serviceName;
        this.hourlyRate = hourlyRate;
    }

    public void setServiceName(String serviceName){
        this.serviceName = serviceName;
    }

    public String getServiceName(){
        return this.serviceName;
    }

    public void setHourlyRate(double hourlyRate){
        this.hourlyRate = hourlyRate;
    }

    public double getHourlyRate(){
        return this.hourlyRate;
    }

    public void addProvider(Provider provider) {
        this.providers.add(provider);
    }

    public List<Provider> getProviders() {
        return this.providers;
    }

}
