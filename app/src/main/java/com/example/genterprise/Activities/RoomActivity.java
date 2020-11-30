package com.example.genterprise.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.genterprise.Controller.DataController;
import com.example.genterprise.Model.Devices;
import com.example.genterprise.Model.LightModel;
import com.example.genterprise.Model.RoomModel;
import com.example.genterprise.R;
import com.example.genterprise.Service.DeviceFetchingService;
import com.example.genterprise.View.DeviceAdapter;
import com.example.genterprise.View.FloorAdapter;
import com.example.genterprise.View.RoomAdapter;

public class RoomActivity extends AppCompatActivity {

    private DataController dataController;
    private RecyclerView recyclerView;
    private RoomAdapter roomAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        recyclerView = findViewById(R.id.floor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        dataController = new DataController(new DeviceFetchingService());

        roomAdapter = new RoomAdapter(dataController.getRoomModels(), this);
        recyclerView.setAdapter(roomAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (dataController.getRoomModels().size() == 0) {
                        Thread.sleep(10);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            roomAdapter = new RoomAdapter(dataController.getRoomModels(), getApplicationContext());
                            recyclerView.setAdapter(roomAdapter);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}