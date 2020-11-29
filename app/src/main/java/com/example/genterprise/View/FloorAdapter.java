package com.example.genterprise.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.genterprise.Activities.RoomActivity;
import com.example.genterprise.Model.Devices;
import com.example.genterprise.Model.FloorModel;
import com.example.genterprise.Model.RoomModel;
import com.example.genterprise.R;

import java.util.List;

public class FloorAdapter extends RecyclerView.Adapter<FloorAdapter.ViewHolder> {

    List<FloorModel> modelList;
    Context context;

    public FloorAdapter(List<FloorModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public FloorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.floor_recycler_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FloorAdapter.ViewHolder holder, int position) {
        FloorModel floorModel = modelList.get(position);
        String usr = floorModel.getName();
        holder.nameTextView.setText(usr);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.floorName);
            nameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, RoomActivity.class);
                    Bundle b = new Bundle();

                    context.startActivity(intent);
                }
            });
        }
    }
}