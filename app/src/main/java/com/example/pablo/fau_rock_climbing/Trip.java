package com.example.pablo.fau_rock_climbing;

/**
 * Created by Pablo on 4/5/2018.
 */

public class Trip {
    private String name, e_date, s_date;
    private int id;


    public Trip(String name, String s_date, String e_date, int id){
        this.name = name;
        this.id= id;
        this.s_date = s_date;
        this.e_date = e_date;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public String getS_date(){
        return  s_date;
    }

    public String getE_date(){
        return e_date;
    }

    public void setS_date(String date){
        s_date = date;
    }

    public void setE_date(String date){
        e_date = date;
    }
}
