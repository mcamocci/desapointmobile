package com.desapoint.desapoint.pojos;

/**
 * Created by root on 3/12/17.
 */

public class Subject {

    public static String NAME="SUBJECT";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject_code() {
        return subject_code;
    }

    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    private int id;
    private String subject_code;
    private String subject;
}
