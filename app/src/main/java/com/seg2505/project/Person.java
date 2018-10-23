package com.seg2505.project;

/**
 * Diedrick Ng
 * Kame House
 */
public class Person {

    //Person Attributes
    private String name;
    private String lastName;

    public Person(String aName, String aLastName) {
        name = aName;
        lastName = aLastName;
    }

    public String getName() {
        return name;
    }


    public String getLastName() {
        return lastName;
    }

    public String toString() {
        return super.toString() + "["+
                        "name" + ":" + getName()+ "," +
                        "lastName" + ":" + getLastName()+ "]";
    }
}
