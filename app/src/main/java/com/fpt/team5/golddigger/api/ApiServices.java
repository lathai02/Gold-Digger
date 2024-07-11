package com.fpt.team5.golddigger.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServices {
    public static final String BASE_URL = "https://webapi.dantri.com.vn/";
    private final GoldPriceApiEndpoint goldPriceApiEndpoint;

    private static ApiServices apiServices = null;

    public static ApiServices getInstance() {
        if (apiServices == null) {
            apiServices = new ApiServices();
        }
        return apiServices;
    }


    private ApiServices() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        goldPriceApiEndpoint = retrofit.create(GoldPriceApiEndpoint.class);
    }

    public static GoldPriceApiEndpoint getCommentApiEndpoint() {
        return getInstance().goldPriceApiEndpoint;
    }
}
