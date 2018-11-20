package com.seg2505.project.model;

public class ProviderInfo{

    private String phoneNumber;
    private String companyName;
    private String description;
    private boolean licensed;
    public ProviderInfo(){

    }

    public ProviderInfo(String phoneNumber, String companyName, String description, boolean licensed) {
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.description = description;
        this.licensed = licensed;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean getLicensed() {
        return this.licensed;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLicensed(boolean licensed) {
        this.licensed = licensed;
    }
}
