package com.example.genterprise.Model;

import java.util.List;

public class FloorModel {

    private String name;
    public List<RoomModel> roomModelList;

    @Override
    public String toString() {
        return "FloorModel{" +
                "name='" + name + '\'' +
                ", roomModelList=" + roomModelList +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RoomModel> getRoomModelList() {
        return roomModelList;
    }

    public void setRoomModelList(List<RoomModel> roomModelList) {
        this.roomModelList = roomModelList;
    }

    public FloorModel(String name, List<RoomModel> roomModelList) {
        this.name = name;
        this.roomModelList = roomModelList;
    }
}
