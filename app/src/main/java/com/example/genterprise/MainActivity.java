package com.example.genterprise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.genterprise.Controller.DataController;
import com.example.genterprise.Model.LightModel;
import com.example.genterprise.Service.DeviceFetchingService;
import com.example.genterprise.View.MyRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyRecyclerViewAdapter myAdapter;
    DataController dataController;



    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.light);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        dataController = new DataController(new DeviceFetchingService());

        dataController.addToModelList(new LightModel("test", "hej", "mere-test"));



        Log.d(TAG, "onCreate: " + dataController.getModels());
        myAdapter = new MyRecyclerViewAdapter(dataController.getModels());
        recyclerView.setAdapter(myAdapter);

    }
}
