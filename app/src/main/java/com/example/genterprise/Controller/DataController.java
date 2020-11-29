package com.example.genterprise.Controller;

import android.util.Log;

import com.example.genterprise.Model.Devices;
import com.example.genterprise.Model.FloorModel;
import com.example.genterprise.Model.LightModel;
import com.example.genterprise.Model.RoomModel;
import com.example.genterprise.Service.DeviceFetchingService;
import com.google.gson.Gson;
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
        dfs.connect();
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

    public List<Devices> addDeviceToList(Devices model){
        deviceModelList.add(model);
        Log.d(TAG, "Object added to model list: " + model.toString());
        return deviceModelList;
    }

    public List<RoomModel> addRoomToList(RoomModel model){
        roomModelList.add(model);
        Log.d(TAG, "Object added to model list: " + model.toString());
        return roomModelList;
    }

    public List<FloorModel> addFloorToList(FloorModel model){
        floorModelList.add(model);
        Log.d(TAG, "Object added to model list: " + model.toString());
        return floorModelList;
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
        String id = model.getId();
        for (int i = 0; i < deviceModelList.size(); i++) {
            if (deviceModelList.contains(id)){
                deviceModelList.get(i).setId(newId);
                break;
            }
        }
        return deviceModelList;
    }

    public List<Devices> modifyObjectNameInList(LightModel model, String newName){
        String id = model.getId();
        for (int i = 0; i < deviceModelList.size(); i++) {
            if (deviceModelList.contains(id)){
                deviceModelList.get(i).setName(newName);
                break;
            }
        }

        return deviceModelList;
    }

    public List<Devices> modifyObjectValueInList(LightModel model, int newValue){
        String id = model.getId();
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
                .filter(p -> p.getId().equals(model.getId()))
                .findAny()
                .orElse(null);

        return model;
    }

    public List<Devices> jsonToModel(String model){
        Gson gson = new Gson();
        //Handle received data from server
        //Build LightModel objects based on json from server


        return deviceModelList;
    }

    public List<Devices> addModelToList(Devices model){


        return deviceModelList;
    }

}
