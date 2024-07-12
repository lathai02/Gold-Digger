package com.fpt.team5.golddigger.Model;

import java.text.DecimalFormat;

public class Plan {
    private int id;
    private String title;
    private String description;
    private double amount;
    private int userId;
    private int status;
    private String createDate;
    private String dueDate;

    public Plan(int id, String title, String description, double amount, int userId, int status, String createDate, String dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.userId = userId;
        this.status = status;
        this.createDate = createDate;
        this.dueDate = dueDate;
    }

    public Plan(String title, String description, double amount, int userId, int status, String createDate, String dueDate) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.userId = userId;
        this.status = status;
        this.createDate = createDate;
        this.dueDate = dueDate;
    }

    public String getFormattedAmount() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(amount);
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
