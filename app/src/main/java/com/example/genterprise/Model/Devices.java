package com.example.genterprise.Model;

public class Devices {

    private String name;
    private int value;
    private String id;


    public Devices(String name, int value, String id) {
        this.name = name;
        this.value = value;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Devices{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", id=" + id +
                '}';
    }
}
