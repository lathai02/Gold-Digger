
package com.fpt.team5.golddigger.api.interestRate.ApiResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("interestRates")
    @Expose
    private List<InterestRate> interestRates;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<InterestRate> getInterestRates() {
        return interestRates;
    }

    public void setInterestRates(List<InterestRate> interestRates) {
        this.interestRates = interestRates;
    }

}
