package com.ieee.ieee_yesist.model;

import android.location.Location;

import com.google.firebase.firestore.GeoPoint;

public class PlaceModel {
    String image1;
    String name;
    String id;
    String location;
    String description;
    Float stars;
    GeoPoint latlang;


    public PlaceModel()
    {

    }

    public String getImage1() {
        return image1;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public Float getStars() {
        return stars;
    }

    public GeoPoint getLatlang() {
        return latlang;
    }
}
