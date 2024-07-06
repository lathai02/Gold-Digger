package com.fpt.team5.golddigger.Model;

public class Budget {
    private int id;
    private String title;
    private int userId;
    private String time;
    private String createDate;

    public Budget() {

    }

    public Budget(int id, String createDate, String time, int userId, String title) {
        this.id = id;
        this.createDate = createDate;
        this.time = time;
        this.userId = userId;
        this.title = title;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
