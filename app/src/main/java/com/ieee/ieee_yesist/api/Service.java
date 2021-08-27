package com.ieee.ieee_yesist.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {
    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://ieeeyesist12.org/phpApp/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static YesistAPI api = retrofit.create(YesistAPI.class);
}
