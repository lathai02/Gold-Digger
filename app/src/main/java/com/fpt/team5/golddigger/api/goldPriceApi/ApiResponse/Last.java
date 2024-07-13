
package com.fpt.team5.golddigger.api.goldPriceApi.ApiResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Last {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("buyPrice")
    @Expose
    private double buyPrice;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("highestBuyPrice")
    @Expose
    private double highestBuyPrice;
    @SerializedName("lowestBuyPrice")
    @Expose
    private double lowestBuyPrice;
    @SerializedName("prices")
    @Expose
    private List<Price> prices;
    @SerializedName("sellPrice")
    @Expose
    private double sellPrice;
    @SerializedName("sourceUpdatedAt")
    @Expose
    private String sourceUpdatedAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public double getHighestBuyPrice() {
        return highestBuyPrice;
    }

    public void setHighestBuyPrice(double highestBuyPrice) {
        this.highestBuyPrice = highestBuyPrice;
    }

    public double getLowestBuyPrice() {
        return lowestBuyPrice;
    }

    public void setLowestBuyPrice(double lowestBuyPrice) {
        this.lowestBuyPrice = lowestBuyPrice;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getSourceUpdatedAt() {
        return sourceUpdatedAt;
    }

    public void setSourceUpdatedAt(String sourceUpdatedAt) {
        this.sourceUpdatedAt = sourceUpdatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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
