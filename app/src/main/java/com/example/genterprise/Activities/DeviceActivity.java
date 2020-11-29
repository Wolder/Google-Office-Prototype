package com.example.genterprise.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.genterprise.Controller.DataController;
import com.example.genterprise.Model.BlindsModel;
import com.example.genterprise.Model.CO2Model;
import com.example.genterprise.Model.Devices;
import com.example.genterprise.Model.FloorModel;
import com.example.genterprise.Model.LightModel;
import com.example.genterprise.R;
import com.example.genterprise.Service.DeviceFetchingService;
import com.example.genterprise.View.DeviceAdapter;
import com.example.genterprise.View.FloorAdapter;

public class DeviceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DataController dataController;
    private DeviceAdapter deviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        recyclerView = findViewById(R.id.device);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        dataController = new DataController(new DeviceFetchingService());

        dataController.addDeviceToList(new LightModel("Light-1", 100, "UID-1"));
        dataController.addDeviceToList(new CO2Model("Light-1", 100, "UID-1"));
        dataController.addDeviceToList(new BlindsModel("Light-1", 100, "UID-1"));

        deviceAdapter = new DeviceAdapter(dataController.getDeviceModels());
        recyclerView.setAdapter(deviceAdapter);
    }
}