package com.example.genterprise.Model;

import java.util.List;

public class RoomModel {

    public List<Devices> deviceModelList;
    public String name;

    public RoomModel(String name, List<Devices> deviceModelList) {
        this.name = name;
        this.deviceModelList = deviceModelList;
    }
    public RoomModel(String name){
        this.name = name;
    }

    public void setDeviceModelList(List<Devices> devices) {
        this.deviceModelList = devices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RoomModel{" +
                "name='" + name + '\'' +
                ", devices=" + deviceModelList +
                '}';
    }
}