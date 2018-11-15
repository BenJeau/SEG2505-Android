package com.seg2505.project.model;

/**
 * Diedrick Ng
 * Kame House
 */
public class Address {
    private int streetNum;
    private String streetName;
    private String city;
    private String province;
    private String country;

    public Address(int streetNum, String streetName, String city, String province, String country ){
        this.streetNum = streetNum;
        this.streetName = streetName;
        this.city = city;
        this.province = province;
        this.country = country;
    }

   
}
