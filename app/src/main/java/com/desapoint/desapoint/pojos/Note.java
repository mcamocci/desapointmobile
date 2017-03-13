package com.desapoint.desapoint.pojos;

/**
 * Created by root on 3/11/17.
 */
public class Note {

    private int id;
    private String subject;
    private String notes_name;
    private String description;
    private String notes_upload;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNotes_name() {
        return notes_name;
    }

    public void setNotes_name(String notes_name) {
        this.notes_name = notes_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes_upload() {
        return notes_upload;
    }

    public void setNotes_upload(String notes_upload) {
        this.notes_upload = notes_upload;
    }

    public String getNotes_category() {
        return notes_category;
    }

    public void setNotes_category(String notes_category) {
        this.notes_category = notes_category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getNotes_year() {
        return notes_year;
    }

    public void setNotes_year(String notes_year) {
        this.notes_year = notes_year;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String notes_category;
    private String date;
    private String university;
    private String notes_year;
    private String user_name;
    private String status;

}
