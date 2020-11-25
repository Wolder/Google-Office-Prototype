package com.example.genterprise.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.genterprise.Model.LightModel;
import com.example.genterprise.R;
import com.example.genterprise.Service.IDeviceFetching;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    List<LightModel> modelList;

    public MyRecyclerViewAdapter(List<LightModel> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, int position) {
        LightModel lightModel = modelList.get(position);
        String usr = lightModel.getName();
        String id = lightModel.getId();

        holder.nameTextView.setText(usr);
        holder.idTextView.setText(id);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView idTextView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.lightName);
            idTextView = itemView.findViewById(R.id.lightId);
        }
    }
}
