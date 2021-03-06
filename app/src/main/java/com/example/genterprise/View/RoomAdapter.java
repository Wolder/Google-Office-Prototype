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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.genterprise.Activities.DeviceActivity;
import com.example.genterprise.Activities.RoomActivity;
import com.example.genterprise.CONSTANTS;
import com.example.genterprise.Controller.DataController;
import com.example.genterprise.Model.Devices;
import com.example.genterprise.Model.RoomModel;
import com.example.genterprise.Model.UserAccessModel;
import com.example.genterprise.R;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    List<RoomModel> modelList;
    Context context;
    FragmentManager manager;
    int floorI;

    public RoomAdapter(List<RoomModel> modelList, Context context, FragmentManager manager, int floorI) {
        this.modelList = modelList;
        this.context = context;
        this.manager = manager;
        this.floorI = floorI;
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

        holder.itemView.setVisibility(View.GONE);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();

        holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));

        holder.nameTextView.setText(usr);
        holder.nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DeviceActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("room_iterable", position);
                intent.putExtra("floor_iterable", floorI);
                context.startActivity(intent);
            }
        });
        holder.lights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new LightDialogFragment(model);
                dialog.show(manager, "LightDialogFragment");
            }
        });

        // Add item if it is accessible
        if (model.getUserAccess().size() == 0) {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setLayoutParams(params);
        } else {
            for (UserAccessModel userAccessModel : model.getUserAccess()) {
                if (userAccessModel.getName().equals(CONSTANTS.CURRENT_USER_ID)) {
                    holder.itemView.setVisibility(View.VISIBLE);
                    holder.itemView.setLayoutParams(params);
                }
            }
        }
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
}
