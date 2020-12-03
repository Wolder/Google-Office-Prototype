package com.example.genterprise.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.genterprise.Controller.DataController;
import com.example.genterprise.Model.Devices;
import com.example.genterprise.R;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    List<Devices> modelList;

    public DeviceAdapter(List<Devices> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceAdapter.ViewHolder holder, int position) {
        Devices lightModel = modelList.get(position);
        String usr = lightModel.getType();
        double value = lightModel.getValue();

        holder.seeker.setVisibility(View.GONE);
        if(lightModel.getType().contains("light")){
            holder.seeker.setVisibility(View.VISIBLE);
            holder.seeker.setProgress((int)lightModel.getValue());

            holder.seeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    lightModel.setValue(progress);
                    holder.idTextView.setText(progress + "");

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    DataController.getInstance().updateDatabase();
                }
            });
        }

        holder.nameTextView.setText(usr);
        holder.idTextView.setText(String.valueOf(value));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView idTextView;
        SeekBar seeker;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            seeker = itemView.findViewById(R.id.seekBar1);
            nameTextView = itemView.findViewById(R.id.lightName);
            idTextView = itemView.findViewById(R.id.lightId);

        }
    }
}
