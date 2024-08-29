package com.example.fooddelivery.models;

public class User {
    private String id;
    private String emailAddress;
    private String password;
    private String ImagePath;
    private String PhoneNumber;

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User(String id, String emailAddress, String password, String imagePath, String phoneNumber) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.password = password;
        ImagePath = imagePath;
        PhoneNumber = phoneNumber;
    }



    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
