package com.fpt.team5.golddigger.Model;

public class GoldPrice {
    private String imageLink;
    private String type;
    private String buyPrice;
    private String sellPrice;
    private String buyPriceChange;
    private String sellPriceChange;

    public GoldPrice() {
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getBuyPriceChange() {
        return buyPriceChange;
    }

    public void setBuyPriceChange(String buyPriceChange) {
        this.buyPriceChange = buyPriceChange;
    }

    public String getSellPriceChange() {
        return sellPriceChange;
    }

    public void setSellPriceChange(String sellPriceChange) {
        this.sellPriceChange = sellPriceChange;
    }

    @Override
    public String toString() {
        return "GoldPrice{" +
                "imageLink='" + imageLink + '\'' +
                ", type='" + type + '\'' +
                ", buyPrice='" + buyPrice + '\'' +
                ", buyPriceChange='" + buyPriceChange + '\'' +
                ", sellPriceChange='" + sellPriceChange + '\'' +
                '}';
    }
}
