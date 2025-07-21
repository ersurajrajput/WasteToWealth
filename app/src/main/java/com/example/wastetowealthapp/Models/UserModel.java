package com.example.wastetowealthapp.Models;

public class UserModel {
    private String email;
    private String name;
    private String mob;
    private String pass;
    private String userType;

    public UserModel(){}

    public UserModel(String email, String name, String mob, String pass, String userType) {
        this.email = email;
        this.name = name;
        this.mob = mob;
        this.pass = pass;
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
