
package com.fpt.team5.golddigger.api.interestRate.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InterestRate {

    @SerializedName("deposit")
    @Expose
    private int deposit;
    @SerializedName("value")
    @Expose
    private Object value;

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
