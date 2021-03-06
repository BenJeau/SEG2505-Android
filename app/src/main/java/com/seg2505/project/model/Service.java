package com.seg2505.project.model;

import com.seg2505.project.model.Provider;

import java.util.*;
/**
 * Diedrick Ng
 * Kame House
 */
public class Service {

    //Service Associations
    private List<String> providers;
    private String serviceName;
    private double hourlyRate;
    private String serviceId;

    // CONSTRUCTOR
    public Service(String serviceName, double hourlyRate, String serviceId){
        providers = new ArrayList<String>();

        this.serviceName = serviceName;
        this.hourlyRate = hourlyRate;
        this.serviceId=serviceId;
    }

    public Service(){
        providers = new ArrayList<String>();
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

    public void addProvider(String provider) {
        if (providers == null) {
            providers = new ArrayList<String>();
        }

        this.providers.add(provider);
    }

    public List<String> getProviders() {
        return this.providers;
    }

    public String getServiceId() {
        return serviceId;
    }

    @Override
    public boolean equals(Object obj) {
        Service s = (Service) obj;
        return s.serviceId.equals(this.serviceId);
    }
}
