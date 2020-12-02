package com.example.genterprise.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.genterprise.Controller.DataController;
import com.example.genterprise.R;
import com.example.genterprise.View.NewRoomDialogFragment;
import com.example.genterprise.View.RoomAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RoomActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RoomAdapter roomAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        recyclerView = findViewById(R.id.floor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        roomAdapter = new RoomAdapter(DataController.getInstance().getRoomModels(), this, getSupportFragmentManager());
        recyclerView.setAdapter(roomAdapter);

        // Adding new Rooms to the floor
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Floor based on index in list
                int floorIterable = getIntent().getIntExtra("floor_iterable", 0);
                DialogFragment dialog = new NewRoomDialogFragment(floorIterable);
                dialog.show(getSupportFragmentManager(), "NewRoomDialogFragmnet");

            }
        });

        // Updater Thread
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
                            roomAdapter = new RoomAdapter(DataController.getInstance().getRoomModels(), getApplicationContext(), getSupportFragmentManager());
                            recyclerView.setAdapter(roomAdapter);
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