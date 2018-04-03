package com.example.pablo.fau_rock_climbing;

/**
 * Created by Pablo on 3/30/2018.
 */

public class Guest {

    private String name, l_name, username, membership, level;
    private int age;


    public Guest(String name, String l_name, String username, String membership, String level, int age){
        this.name = name;
        this.l_name = l_name;
        this.username = username;
        this.membership = membership;
        this.level = level;
        this.age = age;
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

}
