package com.seg2505.project;

/**
 * Diedrick Ng
 * Kame House
 */
public class Person {

    //Person Attributes
    private String username;
    private String password;
    String role;

    public Person(){

    }

    public Person(String username, String password) {
        this.username = username;
        this.password = password;
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
