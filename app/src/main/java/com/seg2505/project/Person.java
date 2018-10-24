package com.seg2505.project;

/**
 * Diedrick Ng
 * Kame House
 */
public class Person {

    //Person Attributes
    private String email;
    private String password;

    public Person(){

    }
    public Person(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }

    public String toString() {
        return  "["+ "name" + ":" + getEmail()+ "," + "lastName" + ":" + getPassword()+ "]";
    }
}
