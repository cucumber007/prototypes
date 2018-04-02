package com.cucumber007.prototypes.cases;

public class Payload {

    private String data;
    private String name;

    public Payload(String name, String data) {
        this.data = data;
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + data.hashCode();
    }
}
