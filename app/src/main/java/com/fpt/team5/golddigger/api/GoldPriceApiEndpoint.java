package com.fpt.team5.golddigger.api;

import com.fpt.team5.golddigger.api.ApiResponse.GoldPriceResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GoldPriceApiEndpoint {

    @GET("gia-vang")
    Call<List<GoldPriceResponse>> getAlls();
}
