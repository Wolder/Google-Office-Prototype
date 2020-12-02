package com.example.genterprise.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.genterprise.Controller.DataController;
import com.example.genterprise.Model.RoomModel;
import com.example.genterprise.R;

public class LightDialogFragment extends DialogFragment {
    RoomModel model;

    public LightDialogFragment(RoomModel model) {
        this.model = model;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View mView = inflater.inflate(R.layout.popup_window_light, null);
        builder.setView(mView);
        builder.setMessage("Set brightness for lights in room")
                .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        model.setAllLights(((SeekBar)mView.findViewById(R.id.seekBar)).getProgress());
                        DataController.getInstance().updateDatabase();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        SeekBar seekBar = mView.findViewById(R.id.seekBar);
        TextView progress = mView.findViewById(R.id.seekBarProgress);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress.setText(i + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}