package com.fpt.team5.golddigger.Model;

public class User {
    private int id;
    private String email;
    private String phone;
    private String name;
    private String password;
    private int imageId;

    public User() {
    }

    public User(int id, String email, String phone, String name, String password, int imageId) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.password = password;
        this.imageId = imageId;
    }

    public User(String email, String phone, String name, String password, int imageId) {
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.password = password;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
