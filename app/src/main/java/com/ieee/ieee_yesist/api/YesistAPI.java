package com.ieee.ieee_yesist.api;

import com.google.gson.JsonArray;
import com.ieee.ieee_yesist.model.SponsorList;
import com.ieee.ieee_yesist.model.WebinarItem;

import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface YesistAPI {

    @GET("/webinar.php")
    Call<JSONArray> getWebinarDetails();

}
