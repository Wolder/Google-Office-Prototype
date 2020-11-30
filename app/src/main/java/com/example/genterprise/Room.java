package com.example.genterprise;

import java.util.List;

// TODO -> TEST IMPLEMENTATION
public class Room{
    public String roomname;
    public List<Device> devices;

    @Override
    public String toString(){
        String deviceString = "";
        for(int i = 0; i < devices.size(); i++) {
            deviceString = deviceString + devices.get(i).toString();
        }
        return
                "\t RoomName: " + roomname +
                        " Devices: " + deviceString + "\n";
    }
}