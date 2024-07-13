package com.fpt.team5.golddigger.api.interestRate;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServices {
    public static final String BASE_URL = "https://s.cafef.vn/ajax/";
    private final BankInterestRateApiEndpoint bankInterestRateApiEndpoint;

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
        bankInterestRateApiEndpoint = retrofit.create(BankInterestRateApiEndpoint.class);
    }

    public static BankInterestRateApiEndpoint getCommentApiEndpoint() {
        return getInstance().bankInterestRateApiEndpoint;
    }
}
