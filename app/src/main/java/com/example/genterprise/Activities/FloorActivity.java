package com.example.genterprise.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.genterprise.Controller.DataController;
import com.example.genterprise.Model.Devices;
import com.example.genterprise.Model.FloorModel;
import com.example.genterprise.Model.LightModel;
import com.example.genterprise.Model.RoomModel;
import com.example.genterprise.R;
import com.example.genterprise.Service.DeviceFetchingService;
import com.example.genterprise.View.FloorAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FloorActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloorAdapter myAdapter;

    public static final String TAG = "FloorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.floor);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        myAdapter = new FloorAdapter(DataController.getInstance().getFloorModels(), this);
        recyclerView.setAdapter(myAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!DataController.getInstance().isDataUpdated()) {
                        Thread.sleep(10);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myAdapter = new FloorAdapter(DataController.getInstance().getFloorModels(), getApplicationContext());
                            recyclerView.setAdapter(myAdapter);
                            DataController.getInstance().setDataUpdated(false);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
