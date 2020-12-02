package com.example.genterprise.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.genterprise.Activities.DeviceActivity;
import com.example.genterprise.Activities.RoomActivity;
import com.example.genterprise.Controller.DataController;
import com.example.genterprise.Model.Devices;
import com.example.genterprise.Model.RoomModel;
import com.example.genterprise.R;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    List<RoomModel> modelList;
    Context context;

    public RoomAdapter(List<RoomModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public RoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.floor_recycler_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.ViewHolder holder, int position) {
        RoomModel model = modelList.get(position);
        String usr = model.getName();

        holder.nameTextView.setText(usr);
        holder.nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DeviceActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("room_iterable", position);
                context.startActivity(intent);
            }
        });
        holder.lights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonShowPopupWindowClick(view, model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView lights;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.floorName);
            lights = itemView.findViewById(R.id.trigger);
        }
    }

    public void onButtonShowPopupWindowClick(View view, RoomModel model) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window_light, null);
        popupView.setElevation(50);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout linearLayout = new LinearLayout(popupView.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);

        TextView infoText = new TextView(popupView.getContext());
        infoText.setLayoutParams(params);
        infoText.setText("Set brightness for all lights:");

        SeekBar seekBar = new SeekBar(popupView.getContext());
        seekBar.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        seekBar.setMin(0);
        seekBar.setMax(100);
        seekBar.setProgress(50);

        TextView seekBarProgress = new TextView(popupView.getContext());
        seekBarProgress.setLayoutParams(params);
        seekBarProgress.setText("50%");

        Button saveButton = new Button(popupView.getContext());
        saveButton.setLayoutParams(params);
        saveButton.setText("Save");

        linearLayout.addView(infoText);
        linearLayout.addView(seekBar);
        linearLayout.addView(seekBarProgress);
        linearLayout.addView(saveButton);

        final PopupWindow[] popupWindow = {new PopupWindow(linearLayout, width, height, focusable)};


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBarProgress.setText(i + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setAllLights(seekBar.getProgress());
                DataController.getInstance().updateDatabase();
                Toast.makeText(view.getContext(),model.name + " : Lights set to " + seekBar.getProgress() + "%",Toast.LENGTH_SHORT).show();

                popupWindow[0].dismiss();
            }
        });



        popupWindow[0].showAtLocation(view, Gravity.CENTER, 0, 0);

/*
        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

 */
    }
}
