package com.fpt.team5.golddigger.Model;

import java.text.DecimalFormat;

public class Budget {
    private int id;
    private String title;
    private int userId;
    private String createDate;
    private double amount;

    public Budget(int id, String title, int userId, String createDate, double amount) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.createDate = createDate;
        this.amount = amount;
    }

    public Budget(String title, int userId, String createDate, double amount) {
        this.title = title;
        this.userId = userId;
        this.createDate = createDate;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public String getFormattedAmount() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(amount);
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
