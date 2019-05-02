package com.example.ecommerceapp.Models;

public class UserModel {

    private String fName, lName, date, phone, address, image;

    public UserModel(String fName, String lName, String date, String phone, String address, String image) {
        this.fName = fName;
        this.lName = lName;
        this.date = date;
        this.phone = phone;
        this.address = address;
        this.image = image;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getDate() {
        return date;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }
}