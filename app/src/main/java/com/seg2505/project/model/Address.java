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

    public Address() {}

    public Address(int streetNum, String streetName, String city, String province, String country ){
        this.streetNum = streetNum;
        this.streetName = streetName;
        this.city = city;
        this.province = province;
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setStreetNum(int streetNum) {
        this.streetNum = streetNum;
    }

    public int getStreetNum() {
        return streetNum;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public String getStreetName() {
        return streetName;
    }
}
