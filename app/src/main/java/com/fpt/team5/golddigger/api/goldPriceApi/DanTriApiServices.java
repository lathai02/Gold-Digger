package com.fpt.team5.golddigger.api.goldPriceApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DanTriApiServices {
    public static final String BASE_URL = "https://webapi.dantri.com.vn/";
    private final GoldPriceApiEndpoint goldPriceApiEndpoint;

    private static DanTriApiServices danTriApiServices = null;

    public static DanTriApiServices getInstance() {
        if (danTriApiServices == null) {
            danTriApiServices = new DanTriApiServices();
        }
        return danTriApiServices;
    }


    private DanTriApiServices() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        goldPriceApiEndpoint = retrofit.create(GoldPriceApiEndpoint.class);
    }

    public static GoldPriceApiEndpoint getCommentApiEndpoint() {
        return getInstance().goldPriceApiEndpoint;
    }
}
