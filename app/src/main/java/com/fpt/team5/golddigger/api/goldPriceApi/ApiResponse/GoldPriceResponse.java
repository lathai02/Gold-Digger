
package com.fpt.team5.golddigger.api.goldPriceApi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoldPriceResponse {

    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("last")
    @Expose
    private Last last;
    @SerializedName("chart")
    @Expose
    private Chart chart;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Last getLast() {
        return last;
    }

    public void setLast(Last last) {
        this.last = last;
    }

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

}
