package com.desapoint.desapoint.pojos;

/**
 * Created by root on 3/23/17.
 */

public class Assignment {

    private String poster;
    private String description;
    private String url;
    private int id;
    private String date_offered;
    private String date_return;

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate_offered() {
        return date_offered;
    }

    public void setDate_offered(String date_offered) {
        this.date_offered = date_offered;
    }

    public String getDate_return() {
        return date_return;
    }

    public void setDate_return(String date_return) {
        this.date_return = date_return;
    }
}
