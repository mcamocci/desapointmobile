package com.desapoint.desapoint.pojos;

/**
 * Created by root on 3/14/17.
 */

public class Category {

    public Category(){

    }

    private int id;
    private String category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNumber_books() {
        return number_books;
    }

    public void setNumber_books(int number_books) {
        this.number_books = number_books;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    private int number_books;
    private String university;

}
