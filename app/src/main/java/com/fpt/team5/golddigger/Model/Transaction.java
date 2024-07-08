package com.fpt.team5.golddigger.Model;

public class Transaction {
    private int id;
    private String title;
    private int userId;
    private String description;
    private String amount;
    private int categoryId;
    private int subCategoryId;
    private String createDate;
    private String dueDate;

    public Transaction() {
    }

    public Transaction(int id, String title, int userId, String description, String amount, int categoryId, int subCategoryId, String createDate) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.description = description;
        this.amount = amount;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.createDate = createDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Transaction(String dueDate, String createDate, int subCategoryId, int categoryId, String amount, String description, int userId, String title, int id) {
        this.dueDate = dueDate;
        this.createDate = createDate;
        this.subCategoryId = subCategoryId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.description = description;
        this.userId = userId;
        this.title = title;
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
