package com.example.genterprise;

import java.util.List;

// TODO -> TEST IMPLEMENTATION
public class Root{
    public String name;
    public List<Room> rooms;

    @Override
    public String toString(){
        String roomString = "";
        for(int i = 0; i < rooms.size(); i++) {
            roomString = roomString + rooms.get(i).toString();
        }
        return
                "\n Name: " + name +
                " Rooms: \n" + roomString ;
    }
}


