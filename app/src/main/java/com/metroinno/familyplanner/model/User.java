package com.metroinno.familyplanner.model;

import java.util.HashMap;

/**
 * Created by anuj on 3/30/16.
 */
public class User {
    private String name;
    private String email;


    public User(){}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}
