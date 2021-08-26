package com.ieee.ieee_yesist.model;

import com.google.gson.annotations.SerializedName;

public class WebinarItem {

    @SerializedName("webinar_title")
    public String webinarTitle;

    @SerializedName("webinar_track")
    public String webinarTrack;

    @SerializedName("webinar_speaker")
    public String webinarSpeaker;

    @SerializedName("webinar_date")
    public String webinarDate;

    @SerializedName("webinar_start_time")
    public String webinarStartTime;

    @SerializedName("webinar_end_time")
    public String webinarEndTime;

    @SerializedName("webinar_link")
    public String webinarLink;

    @SerializedName("webinar_desc")
    public String webinarDesc;

    @SerializedName("webinar_speaker_iurl")
    public String webinarSpeakerIurl;

    @SerializedName("webinar_speaker_des")
    public String webinarSpeakerDes;

    public String getWebinarTitle() {
        return webinarTitle;
    }

    public void setWebinarTitle(String webinarTitle) {
        this.webinarTitle = webinarTitle;
    }

    public String getWebinarTrack() {
        return webinarTrack;
    }

    public void setWebinarTrack(String webinarTrack) {
        this.webinarTrack = webinarTrack;
    }

    public String getWebinarSpeaker() {
        return webinarSpeaker;
    }

    public void setWebinarSpeaker(String webinarSpeaker) {
        this.webinarSpeaker = webinarSpeaker;
    }

    public String getWebinarDate() {
        return webinarDate;
    }

    public void setWebinarDate(String webinarDate) {
        this.webinarDate = webinarDate;
    }

    public String getWebinarStartTime() {
        return webinarStartTime;
    }

    public void setWebinarStartTime(String webinarStartTime) {
        this.webinarStartTime = webinarStartTime;
    }

    public String getWebinarEndTime() {
        return webinarEndTime;
    }

    public void setWebinarEndTime(String webinarEndTime) {
        this.webinarEndTime = webinarEndTime;
    }

    public String getWebinarLink() {
        return webinarLink;
    }

    public void setWebinarLink(String webinarLink) {
        this.webinarLink = webinarLink;
    }

    public String getWebinarDesc() {
        return webinarDesc;
    }

    public void setWebinarDesc(String webinarDesc) {
        this.webinarDesc = webinarDesc;
    }

    public String getWebinarSpeakerIurl() {
        return webinarSpeakerIurl;
    }

    public void setWebinarSpeakerIurl(String webinarSpeakerIurl) {
        this.webinarSpeakerIurl = webinarSpeakerIurl;
    }

    public String getWebinarSpeakerDes() {
        return webinarSpeakerDes;
    }

    public void setWebinarSpeakerDes(String webinarSpeakerDes) {
        this.webinarSpeakerDes = webinarSpeakerDes;
    }
}
