
package com.fpt.team5.golddigger.api.goldPriceApi.ApiResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chart {

    @SerializedName("buyPrice")
    @Expose
    private List<List<Long>> buyPrice;
    @SerializedName("sellPrice")
    @Expose
    private List<List<Long>> sellPrice;

    public List<List<Long>> getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(List<List<Long>> buyPrice) {
        this.buyPrice = buyPrice;
    }

    public List<List<Long>> getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(List<List<Long>> sellPrice) {
        this.sellPrice = sellPrice;
    }

}
