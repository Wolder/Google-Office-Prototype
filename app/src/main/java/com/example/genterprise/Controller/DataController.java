package com.example.genterprise.Controller;

import android.util.Log;

import com.example.genterprise.Model.LightModel;
import com.example.genterprise.Service.DeviceFetchingImpl;
import com.example.genterprise.Service.IDeviceFetching;

import java.util.ArrayList;
import java.util.List;

public class DataController {

    private static final String TAG = "DataController";

    IDeviceFetching fetching;
    List<LightModel> modelList = new ArrayList<>();

    public DataController(IDeviceFetching fetching) {
        this.fetching = fetching;
    }

    public List<LightModel> getModels(){
        return modelList;
    }

    public List<LightModel> addToModelList(LightModel model){
        modelList.add(model);
        Log.d(TAG, "Object added to model list: " + model.toString());
        return modelList;
    }

    public List<LightModel> deleteModelFromList(LightModel model){
        if(modelList.contains(model)){
            modelList.remove(model);
        } else {
            Log.d(TAG, "Object not found in list: " + model.toString());
        }

        return modelList;
    }

    public List<LightModel> modifyObjectIdInList(LightModel model, String newId){
        String id = model.getId();
        for (int i = 0; i < modelList.size(); i++) {
            if (modelList.contains(id)){
                modelList.get(i).setId(newId);
                break;
            }
        }
        return modelList;
    }

    public List<LightModel> modifyObjectNameInList(LightModel model, String newName){
        String id = model.getId();
        for (int i = 0; i < modelList.size(); i++) {
            if (modelList.contains(id)){
                modelList.get(i).setName(newName);
                break;
            }
        }

        return modelList;
    }

    public List<LightModel> modifyObjectManufacturerInList(LightModel model, String newManufacturer){
        String id = model.getId();
        for (int i = 0; i < modelList.size(); i++) {
            if (modelList.contains(id)){
                modelList.get(i).setManufacturer(newManufacturer);
                break;
            }
        }
        return modelList;
    }

    public List<LightModel> modelFromJson(){
        fetching.connectToServer("localhost", 8080);




        return modelList;
    }


}
