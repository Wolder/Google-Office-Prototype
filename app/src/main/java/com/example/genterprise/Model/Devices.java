package com.example.genterprise.Model;

public class Devices {

    private String type;
    private double value;
    private String ID;


    public Devices(String type, double value, String ID) {
        this.type = type;
        this.value = value;
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Devices{" +
                "name='" + type + '\'' +
                ", value=" + value +
                ", id=" + ID +
                '}';
    }
}
