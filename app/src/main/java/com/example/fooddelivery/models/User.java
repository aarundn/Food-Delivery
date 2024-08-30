package com.example.fooddelivery.models;

public class User {
    private String id;
    private String userName;
    private String emailAddress;
    private String address;
    private String password;
    private String ImagePath;
    private String PhoneNumber;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public User(String id, String emailAddress, String password, String imagePath, String phoneNumber, String userName, String address) {
        this.id = id;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.address = address;
        this.password = password;
        this.ImagePath = imagePath;
        this.PhoneNumber = phoneNumber;
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
