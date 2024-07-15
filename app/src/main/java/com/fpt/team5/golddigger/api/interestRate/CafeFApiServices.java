package com.fpt.team5.golddigger.api.interestRate;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CafeFApiServices {
    public static final String BASE_URL = "https://s.cafef.vn/ajax/";
    private final BankInterestRateApiEndpoint bankInterestRateApiEndpoint;

    private static CafeFApiServices cafeFApiServices = null;

    public static CafeFApiServices getInstance() {
        if (cafeFApiServices == null) {
            cafeFApiServices = new CafeFApiServices();
        }
        return cafeFApiServices;
    }


    private CafeFApiServices() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        bankInterestRateApiEndpoint = retrofit.create(BankInterestRateApiEndpoint.class);
    }

    public static BankInterestRateApiEndpoint getCommentApiEndpoint() {
        return getInstance().bankInterestRateApiEndpoint;
    }
}
