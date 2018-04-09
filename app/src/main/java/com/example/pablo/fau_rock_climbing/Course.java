package com.example.pablo.fau_rock_climbing;

public class Course {

    private String name, level, price;
    private String [] instructors;

    public Course(String name, String level, String price, String[] instr) {
        this.name = name;
        this.level = level;
        this.price = price;
        instructors = instr;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String[] getInstructors() {
        return instructors;
    }

    public void setInstructors(String[] instructors) {
        this.instructors = instructors;
    }
}
