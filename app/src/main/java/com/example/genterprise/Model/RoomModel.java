package com.example.genterprise.Model;

import android.util.Log;

import com.example.genterprise.Controller.DataController;

import java.util.List;

public class RoomModel {

    public List<Devices> devices;
    public String name;
    private static final String TAG = "RoomModel";

    public RoomModel(String name, List<Devices> deviceModelList) {
        this.name = name;
        this.devices = devices;
    }
    public RoomModel(String name){
        this.name = name;
    }

    public void setDeviceModelList(List<Devices> devices) {
        this.devices = devices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAllLights(double value) {
        for (Devices device : devices) {
            if (device.getType().contains("light")) {
                device.setValue(value);
                DataController.getInstance().updateDatabase();

            }
        }
    }

    @Override
    public String toString() {
        return "RoomModel{" +
                "name='" + name + '\'' +
                ", devices=" + devices +
                '}';
    }
}