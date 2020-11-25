package com.example.genterprise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.genterprise.Model.LightModel;
import com.example.genterprise.Service.DeviceFetchingImpl;
import com.example.genterprise.Service.IDeviceFetching;
import com.example.genterprise.View.MyRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    List<LightModel> modelList = new ArrayList<>();
    MyRecyclerViewAdapter myadapter;
    DeviceFetchingImpl data;


    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.light);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        data = new DeviceFetchingImpl();
        data.start();


        Log.d(TAG, "onCreate: " + modelList);
        myadapter = new MyRecyclerViewAdapter(modelList);
        recyclerView.setAdapter(myadapter);

    }
}
