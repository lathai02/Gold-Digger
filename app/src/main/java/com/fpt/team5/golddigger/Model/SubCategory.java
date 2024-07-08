package com.fpt.team5.golddigger.Model;

public class SubCategory {
    private int id;
    private int categoryId;
    private String title;
    private int imageId;

    public SubCategory(int id, int categoryId, String title, int imageId) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.imageId = imageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
