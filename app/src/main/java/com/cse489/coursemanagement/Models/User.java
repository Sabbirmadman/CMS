package com.cse489.coursemanagement.Models;

public class User {
    private String email;
    private String id;
    private String idNumber;
    private String name;
    private String phone;
    private String type;

    public User() {
    }

    public User(String email, String id, String idNumber, String name, String phone, String type) {
        this.email = email;
        this.id = id;
        this.idNumber = idNumber;
        this.name = name;
        this.phone = phone;
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
