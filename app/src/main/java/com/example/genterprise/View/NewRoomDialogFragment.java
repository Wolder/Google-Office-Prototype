package com.example.genterprise.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.genterprise.Controller.DataController;
import com.example.genterprise.Model.RoomModel;
import com.example.genterprise.R;

public class NewRoomDialogFragment extends DialogFragment {
    int floorI;

    public NewRoomDialogFragment(int floorI) {
        this.floorI = floorI;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View mView = inflater.inflate(R.layout.popup_window_new_room, null);
        builder.setView(mView);
        builder.setMessage("Create new Room")
                .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RoomModel newModel = new RoomModel(((EditText)mView.findViewById(R.id.roomname)).getText().toString());
                        DataController.getInstance().createNewRoomModel(newModel, floorI);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });



        // Create the AlertDialog object and return it
        return builder.create();
    }
}

