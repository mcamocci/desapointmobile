package com.desapoint.desapoint.pojos;

/**
 * Created by root on 3/16/17.
 */

public class DownloadableItem {

    public DownloadableItem(){}

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    private String description;
    private String file_url;
}
