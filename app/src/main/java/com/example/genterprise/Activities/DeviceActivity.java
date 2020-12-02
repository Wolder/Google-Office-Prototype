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
import com.example.genterprise.View.RoomAdapter;

public class DeviceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DeviceAdapter deviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        int roomIterable = getIntent().getIntExtra("room_iterable", 0);

        recyclerView = findViewById(R.id.device);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        deviceAdapter = new DeviceAdapter(DataController.getInstance().getRoomModels().get(roomIterable).devices);
        recyclerView.setAdapter(deviceAdapter);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(100);
                        while (!DataController.getInstance().isDataUpdated()) {
                            Thread.sleep(10);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                deviceAdapter = new DeviceAdapter(DataController.getInstance()
                                        .getRoomModels().get(roomIterable).devices);
                                recyclerView.setAdapter(deviceAdapter);
                                DataController.getInstance().setDataUpdated(false);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}