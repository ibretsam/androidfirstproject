package com.example.androidfirstproject.Models;

public class User {
    String id, phoneNumber, fullName, picture;

    public User() {
    }

    public User(String id, String phoneNumber, String fullName, String picture) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
