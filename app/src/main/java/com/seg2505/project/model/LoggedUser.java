package com.seg2505.project.model;

public class LoggedUser {
    public static String id;
    public LoggedUser(){

    }

    public String getId() {
        return id;
    }

    public static void setId(String id) {
        LoggedUser.id = id;
    }
}
