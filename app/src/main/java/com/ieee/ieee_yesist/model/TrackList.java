package com.ieee.ieee_yesist.model;

public class TrackList {
    String trackName;
    Integer imageUrl;
    String firstPrize;
    String secondPrize;

    public TrackList(String trackName, Integer imageUrl, String firstPrize, String secondPrize) {
        this.trackName = trackName;
        this.imageUrl = imageUrl;
        this.firstPrize = firstPrize;
        this.secondPrize = secondPrize;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFirstPrize() {
        return firstPrize;
    }

    public void setFirstPrize(String firstPrize) {
        this.firstPrize = firstPrize;
    }

    public String getSecondPrize() {
        return secondPrize;
    }

    public void setSecondPrize(String secondPrize) {
        this.secondPrize = secondPrize;
    }
}
