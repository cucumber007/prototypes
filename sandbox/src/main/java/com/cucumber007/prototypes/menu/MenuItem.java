package com.cucumber007.prototypes.menu;

public class MenuItem {

    private String name;
    private Class activity;
    private boolean divider = false;


    public MenuItem(String name, Class activity) {
        this.name = name;
        this.activity = activity;
    }

    public MenuItem() {
        divider = true;
    }

    public boolean isDivider() {
        return divider;
    }

    public String getName() {
        return name;
    }


    public Class getActivityClass() {
        return activity;
    }
}
