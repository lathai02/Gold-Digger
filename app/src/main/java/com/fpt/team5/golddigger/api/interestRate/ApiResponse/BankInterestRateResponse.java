
package com.fpt.team5.golddigger.api.interestRate.ApiResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankInterestRateResponse {

    @SerializedName("Data")
    @Expose
    private List<Datum> data;
    @SerializedName("Message")
    @Expose
    private Object message;
    @SerializedName("Success")
    @Expose
    private Boolean success;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
