package com.ieee.ieee_yesist.api;

import com.google.gson.JsonArray;
import com.ieee.ieee_yesist.model.SponsorList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface YesistAPI {

    @GET("webinar.php")
    Call<JsonArray> getWebinarDetails();

}
