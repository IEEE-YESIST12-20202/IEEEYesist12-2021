package com.ieee.ieee_yesist.model;

import com.google.firebase.Timestamp;

public class NotificationModel {

    String title;
    String body;
    Timestamp expiresOn;

    public NotificationModel()
    {
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Timestamp getExpiresOn() {
        return expiresOn;
    }
}
