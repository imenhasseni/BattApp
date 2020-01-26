package com.example.formation1.model;

public class UserModel {
    private String id;
    private String email;
    private String phone;
    private String username;

    public UserModel(String id, String email, String phone, String username) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.username = username;
    }

    public      UserModel() {

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
