package com.example.pablo.fau_rock_climbing;

/**
 * Created by Pablo on 3/30/2018.
 */

public class Guest {

    private String name, l_name, username, membership, level, e_date, password;
    private int age;


    public Guest(String name, String l_name, String username, String membership, String level, int age, String e_date, String password){
        this.name = name;
        this.l_name = l_name;
        this.username = username;
        this.membership = membership;
        this.level = level;
        this.password = password;
        this.age = age;
        this.e_date = e_date;
    }


    public String getGuestName(){
        return name;
    }

    public String getL_name(){
        return l_name;
    }

    public String getUsername(){
        return username;
    }

    public String getMembership(){
        return membership;
    }

    public String getLevel(){
        return level;
    }

    public int getAge(){
        return age;
    }
    public String getE_date(){
        return e_date;
    }

    public void setUsername(String string){
        this.username = string;
    }

    public void setMembership(String string){
        this.membership = string;
    }

    public void setLevel(String string){
        this.level = string;
    }

    public void setE_date(String date){
        e_date = date;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
