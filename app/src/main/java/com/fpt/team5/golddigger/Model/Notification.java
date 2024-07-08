package com.fpt.team5.golddigger.Model;

public class Notification {
    private int id;
    private String title;
    private int userId;
    private String createDate;

    public Notification(int id, String title, int userId, String createDate) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.createDate = createDate;
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
