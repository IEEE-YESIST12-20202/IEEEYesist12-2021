package com.ieee.ieee_yesist.model;

import java.util.List;

public class SponsorList {

    private List<Sponsor> sponsorList;

    public SponsorList(List<Sponsor> sponsorList) {
        this.sponsorList = sponsorList;
    }

    public List<Sponsor> getSponsorList() {
        return sponsorList;
    }

    public void setSponsorList(List<Sponsor> sponsorList) {
        this.sponsorList = sponsorList;
    }
}
