package com.example.madhushikabasnayake.photoshoppe_madhushika.NetworkActivities;

import java.io.Serializable;

/**
 * Created by madhushikabasnayake on 7/27/17.
 */

public class Image implements Serializable {
    private String title;
    private String pngUrl;
    private String webUrl;
    private String date;

    // Constructor
    public Image(String title, String pngUrl, String webUrl, String date) {
        this.title = title;
        this.pngUrl = pngUrl;
        this.webUrl = webUrl;
        this.date = date;
    }

    //Getters
    public String getTitle() {
        return title;
    }

    public String getPngUrl() {
        return pngUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getDate() {
        return date;
    }


    //Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setPngUrl(String pngUrl) {
        this.pngUrl = pngUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

