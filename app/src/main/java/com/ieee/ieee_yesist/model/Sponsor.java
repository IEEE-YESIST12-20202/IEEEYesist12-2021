package com.ieee.ieee_yesist.model;

import com.google.gson.annotations.SerializedName;

public class Sponsor {

    @SerializedName("sponsor_id")
    private int id;

    @SerializedName("sponsor_image")
    private String imgUrl;

    @SerializedName("sponsor_url")
    private String webUrl;

    public Sponsor(int id, String imgUrl, String webUrl) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.webUrl = webUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
