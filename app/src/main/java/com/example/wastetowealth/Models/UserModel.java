package com.example.wastetowealth.Models;

public class UserModel {
    String name,email,mob,dob,utype,pass;

    public UserModel(){}


    public UserModel(String name, String email, String mob, String dob, String utype) {
        this.name = name;
        this.email = email;
        this.mob = mob;
        this.dob = dob;
        this.utype = utype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }
}
