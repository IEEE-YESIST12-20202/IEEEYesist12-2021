package com.ieee.ieee_yesist.api;

import com.ieee.ieee_yesist.model.SponsorList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface YesistAPI {

    @GET("sponsor.php")
    Call<SponsorList> getSponsors();

}
