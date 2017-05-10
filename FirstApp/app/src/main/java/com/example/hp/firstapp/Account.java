package com.example.hp.firstapp;


import java.io.Serializable;
import java.util.ArrayList;


public class Account implements Serializable {
    public String username;
    public String password;
    public String image;
    public String fullname;
    public ArrayList<String> circles;
    public String _id;
    public String _rev = null;
    public String types ;
    public String phone;
    public ArrayList<String> favlist;

    public Account(String fullname,String username, String password,String image,String phone){
        circles = new ArrayList<>();
        favlist=new ArrayList<>();
        this._id=username;
        this.fullname=fullname;
        this.username=username;
        this.password=password;
        this.image=image;
        this.circles.add(" ");
        this.phone=phone;
        this.favlist.add(" ");
        this.types="user";
    }

}