package com.desapoint.desapoint.pojos;

/**
 * Created by root on 3/26/17.
 */

public class University {

    public static String JSON_VARIABLE="JSON_CONTENT";
    private int id;
    private String name;

    public University(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
