package com.example.genterprise.Controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.genterprise.Model.Devices;
import com.example.genterprise.Model.FloorModel;
import com.example.genterprise.Model.LightModel;
import com.example.genterprise.Model.RoomModel;
import com.example.genterprise.Model.UserAccessModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DataController {

    private static DataController instance = new DataController();
    private static final String TAG = "DataController";
    private List<Devices> deviceModelList = new ArrayList<>();
    private List<RoomModel> roomModelList = new ArrayList<>();
    private List<FloorModel> floorModelList = new ArrayList<>();
    private List<UserAccessModel> userAccessList = new ArrayList<>();
    private boolean dataUpdated = false;

    public DataController() {
        fetchDatabaseIntoApp();
        //dfs.connect();
    }

    //Get the only object available
    public static DataController getInstance() {
        return instance;
    }


    public List<Devices> getDeviceModels(RoomModel roomModel) {
        return roomModel.devices;
    }

    public List<RoomModel> getRoomModels() {
        return roomModelList;
    }

    public List<FloorModel> getFloorModels() {
        return floorModelList;
    }

    public void addUserToList(UserAccessModel user) {
        userAccessList.add(user);
        Log.d(TAG, "Object added to userAccess list: " + user.name);
    }

    public void addDeviceToList(Devices model) {
        deviceModelList.add(model);
        Log.d(TAG, "Object added to model list: " + model.toString());
    }

    public void addRoomToList(RoomModel model) {
        roomModelList.add(model);
        Log.d(TAG, "Object added to model list: " + model.toString());
    }

    public void addFloorToList(FloorModel model) {
        floorModelList.add(model);
        Log.d(TAG, "Object added to model list: " + model.toString());
    }


    public List<Devices> deleteModelFromList(LightModel model) {
        if (deviceModelList.contains(model)) {
            deviceModelList.remove(model);
        } else {
            Log.d(TAG, "Object not found in list: " + model.toString());
        }

        return deviceModelList;
    }

    public List<Devices> modifyObjectIdInList(LightModel model, String newId) {
        String id = model.getID();
        for (int i = 0; i < deviceModelList.size(); i++) {
            if (deviceModelList.contains(id)) {
                deviceModelList.get(i).setID(newId);
                break;
            }
        }
        return deviceModelList;
    }

    public List<Devices> modifyObjectNameInList(LightModel model, String newName) {
        String id = model.getID();
        for (int i = 0; i < deviceModelList.size(); i++) {
            if (deviceModelList.contains(id)) {
                deviceModelList.get(i).setType(newName);
                break;
            }
        }

        return deviceModelList;
    }

    public List<Devices> modifyObjectValueInList(LightModel model, int newValue) {
        String id = model.getID();
        for (int i = 0; i < deviceModelList.size(); i++) {
            if (deviceModelList.contains(id)) {
                deviceModelList.get(i).setValue(newValue);
                break;
            }
        }
        return deviceModelList;
    }

    public Devices findAll() {
        for (Devices model : deviceModelList) {
            return model;
        }
        return null;
    }

    public Devices findAny(final Devices model) {
        Devices matchingObject = deviceModelList.stream()
                .filter(p -> p.getID().equals(model.getID()))
                .findAny()
                .orElse(null);

        return model;
    }

    public boolean isDataUpdated() {
        return dataUpdated;
    }

    public void setDataUpdated(boolean isUpdated) {
        this.dataUpdated = isUpdated;
    }

    public List<FloorModel> fetchDatabaseIntoApp() {
        // Get firebase ref
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Fetch rooms
        database.getReference("").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                deviceModelList.clear();
                roomModelList.clear();
                floorModelList.clear();
                for (DataSnapshot room : snapshot.child("rooms").getChildren()) {
                    for (DataSnapshot device : room.child("devices").getChildren()) {
                        // Devices
                        Devices deviceModel = new Devices(
                                device.child("type").getValue(String.class),
                                device.child("value").getValue(Double.class),
                                device.child("id").getValue(String.class)
                        );

                        addDeviceToList(deviceModel);
                    }
                    // Room User Access
                    for (DataSnapshot user : room.child("userAccess").getChildren()) {
                        UserAccessModel userAccessModel = new UserAccessModel(
                                user.child("name").getValue(String.class)
                        );

                        addUserToList(userAccessModel);
                    }

                    // Room
                    RoomModel roomModel = new RoomModel(room.child("name").getValue(String.class));
                    roomModel.setDeviceModelList(deviceModelList);
                    roomModel.setUserAccess(userAccessList);
                    // Clear List
                    deviceModelList = new ArrayList<>();
                    userAccessList = new ArrayList<>();
                    addRoomToList(roomModel);
                }
                // Floor
                FloorModel floorModel = new FloorModel(snapshot.child("name").getValue(String.class));
                floorModel.setRoomModelList(roomModelList);
                addFloorToList(floorModel);
                dataUpdated = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return floorModelList;
    }

    public void updateDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getInstance().getReference();
        for (FloorModel floor : getFloorModels()) {
            ref.child("name").setValue(floor.getName());
            ref.child("rooms").setValue(floor.getRoomModelList());
        }
        dataUpdated = false;
    }

    public void createNewRoomModel(RoomModel roomModel, int floorModelI) {
        roomModelList.add(roomModel);
        floorModelList.get(floorModelI).setRoomModelList(roomModelList);
        updateDatabase();
    }

    public void createNewDevice(Devices newModel, int floorI, int roomI) {
        RoomModel room = roomModelList.get(roomI);
        room.devices.add(newModel);
        floorModelList.get(floorI).setRoomModelList(roomModelList);
        updateDatabase();
    }
}
