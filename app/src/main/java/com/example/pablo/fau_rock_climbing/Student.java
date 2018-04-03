package com.example.pablo.fau_rock_climbing;

/**
 * Created by Pablo on 3/27/2018.
 */
//This class will store student's information

public class Student {
    private int z_number, age;
    private String password, name, l_name, level;

    public Student(int z_number, int age, String password, String name, String l_name, String level){
        this.z_number = z_number;
        this.age = age;
        this.password = password;
        this.name = name;
        this.l_name = l_name;
        this.level = level;
    }

    public int getZ_number(){
        return z_number;
    }

    public void setZ_number(int z){
        z_number = z;
    }

    public int getAge(){
        return age;
    }

    public String getPassword(){
        return password;
    }

    public String getStudentName(){
        return name;
    }

    public String getL_name(){
        return l_name;
    }

    public String getLevel() {
        return level;
    }
}
