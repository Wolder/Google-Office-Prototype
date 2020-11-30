package com.example.genterprise.Controller;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.genterprise.Activities.FloorActivity;
import com.example.genterprise.Model.Devices;
import com.example.genterprise.Model.FloorModel;
import com.example.genterprise.Model.LightModel;
import com.example.genterprise.Model.RoomModel;
import com.example.genterprise.Service.DeviceFetchingService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DataController {

    private static final String TAG = "DataController";
    private List<Devices> deviceModelList = new ArrayList<>();
    private List<RoomModel> roomModelList = new ArrayList<>();
    private List<FloorModel> floorModelList = new ArrayList<>();
    private DeviceFetchingService dfs;

    public DataController(DeviceFetchingService dfs) {
        this.dfs = dfs;
        fetchDatabaseIntoApp();
        //dfs.connect();
    }

    public List<Devices> getDeviceModels(){
        return deviceModelList;
    }

    public List<RoomModel> getRoomModels() {
        return roomModelList;
    }

    public List<FloorModel> getFloorModels(){
        return floorModelList;
    }

    public void addDeviceToList(Devices model){
        deviceModelList.add(model);
        Log.d(TAG, "Object added to model list: " + model.toString());
    }

    public void addRoomToList(RoomModel model){
        roomModelList.add(model);
        Log.d(TAG, "Object added to model list: " + model.toString());
    }

    public void addFloorToList(FloorModel model){
        floorModelList.add(model);
        Log.d(TAG, "Object added to model list: " + model.toString());
    }


    public List<Devices> deleteModelFromList(LightModel model){
        if(deviceModelList.contains(model)){
            deviceModelList.remove(model);
        } else {
            Log.d(TAG, "Object not found in list: " + model.toString());
        }

        return deviceModelList;
    }

    public List<Devices> modifyObjectIdInList(LightModel model, String newId){
        String id = model.getID();
        for (int i = 0; i < deviceModelList.size(); i++) {
            if (deviceModelList.contains(id)){
                deviceModelList.get(i).setID(newId);
                break;
            }
        }
        return deviceModelList;
    }

    public List<Devices> modifyObjectNameInList(LightModel model, String newName){
        String id = model.getID();
        for (int i = 0; i < deviceModelList.size(); i++) {
            if (deviceModelList.contains(id)){
                deviceModelList.get(i).setType(newName);
                break;
            }
        }

        return deviceModelList;
    }

    public List<Devices> modifyObjectValueInList(LightModel model, int newValue){
        String id = model.getID();
        for (int i = 0; i < deviceModelList.size(); i++) {
            if (deviceModelList.contains(id)){
                deviceModelList.get(i).setValue(newValue);
                break;
            }
        }
        return deviceModelList;
    }

    public Devices findAll(){
        for (Devices model : deviceModelList){
            return model;
        }
        return null;
    }

    public Devices findAny(final Devices model){
        Devices matchingObject = deviceModelList.stream()
                .filter(p -> p.getID().equals(model.getID()))
                .findAny()
                .orElse(null);

        return model;
    }

    public List<FloorModel> fetchDatabaseIntoApp() {
        // Get firebase ref
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Fetch rooms
        database.getReference("").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot room: snapshot.child("rooms").getChildren()) {
                    for (DataSnapshot device: room.child("devices").getChildren()) {
                        // Devices
                        Devices deviceModel = new Devices(
                                device.child("type").getValue(String.class),
                                device.child("value").getValue(Double.class),
                                device.child("ID").getValue(String.class));

                        addDeviceToList(deviceModel);
                    }
                    // Room
                    RoomModel roomModel = new RoomModel(room.child("roomname").getValue(String.class));
                    roomModel.setDeviceModelList(deviceModelList);
                    addRoomToList(roomModel);
                }
                // Floor
                FloorModel floorModel = new FloorModel(snapshot.child("name").getValue(String.class));
                floorModel.setRoomModelList(roomModelList);
                addFloorToList(floorModel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return floorModelList;
    }

}
