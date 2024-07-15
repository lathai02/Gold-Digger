package com.fpt.team5.golddigger.api.interestRate;

import com.fpt.team5.golddigger.api.interestRate.ApiResponse.BankInterestRateResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BankInterestRateApiEndpoint {

    @GET("ajaxlaisuatnganhang.ashx")
    Call<BankInterestRateResponse> getBankInterestRates();
}
