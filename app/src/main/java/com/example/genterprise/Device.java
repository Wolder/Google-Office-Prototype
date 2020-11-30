package com.example.genterprise;

// TODO -> TEST IMPLEMENTATION
public class Device{
    public String type;
    public double value;
    public String ID;

    @Override
    public String toString() {
        return " Type:" + type + " Value:" + value + " ID:" + ID + "\n";
    }
}