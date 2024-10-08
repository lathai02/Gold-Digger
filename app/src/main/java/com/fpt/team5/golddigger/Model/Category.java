package com.fpt.team5.golddigger.Model;

public class Category {
    private int id;
    private String title;
    private int imageId;

    public Category(int imageId, String title, int id) {
        this.imageId = imageId;
        this.title = title;
        this.id = id;
    }

    public Category(String title, int imageId) {
        this.title = title;
        this.imageId = imageId;
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

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
