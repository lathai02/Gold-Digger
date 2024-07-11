
package com.fpt.team5.golddigger.api.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Price {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("buyPrice")
    @Expose
    private double buyPrice;
    @SerializedName("sellPrice")
    @Expose
    private double sellPrice;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("buyPriceChange")
    @Expose
    private double buyPriceChange;
    @SerializedName("sellPriceChange")
    @Expose
    private double sellPriceChange;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getBuyPriceChange() {
        return buyPriceChange;
    }

    public void setBuyPriceChange(double buyPriceChange) {
        this.buyPriceChange = buyPriceChange;
    }

    public double getSellPriceChange() {
        return sellPriceChange;
    }

    public void setSellPriceChange(double sellPriceChange) {
        this.sellPriceChange = sellPriceChange;
    }

}
