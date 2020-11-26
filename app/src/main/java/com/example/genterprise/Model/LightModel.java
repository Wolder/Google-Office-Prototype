package com.example.genterprise.Model;

import java.io.Serializable;

public class LightModel implements Serializable {

    private String name;
    private String manufacturer;
    private String id;

    public LightModel(String name, String manufacturer, String id) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LightModel{" +
                "name='" + name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
