package com.seg2505.project.model;

/**
 * Diedrick Ng
 * Kame House
 */
public class Person {

    //Person Attributes
    private String username;
    private String password;
    private String role;

    public Person(){

    }

    public Person(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String toString() {
        return  "["+ "name" + ":" + getUsername()+ "," + "lastName" + ":" + getPassword()+ "]";
    }
}
