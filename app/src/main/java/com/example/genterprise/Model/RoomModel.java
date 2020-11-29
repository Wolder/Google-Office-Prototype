package com.example.genterprise.Model;

import java.util.List;

public class RoomModel extends FloorModel {

    public List<Devices> deviceModelList;


    public RoomModel(String name, List<RoomModel> roomModelList) {
        super(name, roomModelList);
    }

    public RoomModel(String name){
        super(name);
    }
}