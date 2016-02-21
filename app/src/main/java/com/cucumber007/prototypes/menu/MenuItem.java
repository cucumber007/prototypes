package com.cucumber007.prototypes.menu;

public class MenuItem {

    private String name;
    private Class activity;


    public MenuItem(String name, Class activity) {
        this.name = name;
        this.activity = activity;
    }


    public String getName() {
        return name;
    }


    public Class getActivityClass() {
        return activity;
    }
}
