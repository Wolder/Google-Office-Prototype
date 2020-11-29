package com.example.genterprise.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.genterprise.Controller.DataController;
import com.example.genterprise.Model.FloorModel;
import com.example.genterprise.Model.LightModel;
import com.example.genterprise.Model.RoomModel;
import com.example.genterprise.R;
import com.example.genterprise.Service.DeviceFetchingService;
import com.example.genterprise.View.FloorAdapter;

public class FloorActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloorAdapter myAdapter;
    DataController dataController;

    public static final String TAG = "FloorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.floor);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        dataController = new DataController(new DeviceFetchingService());

        dataController.addFloorToList(new FloorModel("Floor-1"));



        Log.d(TAG, "onCreate: " + dataController.getFloorModels());
        myAdapter = new FloorAdapter(dataController.getFloorModels(), this);
        recyclerView.setAdapter(myAdapter);

    }
}
