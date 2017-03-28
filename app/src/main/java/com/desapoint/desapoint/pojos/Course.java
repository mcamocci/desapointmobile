package com.desapoint.desapoint.pojos;

/**
 * Created by root on 3/27/17.
 */

public class Course {

    public static String JSON_VARIABLE="COURSE_JSON_VAR";
    private String university;
    private String course_name;
    private int id;


    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCourse() {
        return course_name;
    }

    public void setCourse(String course) {
        this.course_name = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
