package com.ieee.ieee_yesist.model;

public class Sponsor {

    private String id;
    private String imgUrl;
    private String webUrl;

    public Sponsor(String id, String imgUrl, String webUrl) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.webUrl = webUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
