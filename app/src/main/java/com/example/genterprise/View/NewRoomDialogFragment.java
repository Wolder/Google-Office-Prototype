package com.example.genterprise.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.genterprise.CONSTANTS;
import com.example.genterprise.Controller.DataController;
import com.example.genterprise.Model.RoomModel;
import com.example.genterprise.Model.UserAccessModel;
import com.example.genterprise.R;

import java.util.ArrayList;
import java.util.List;

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

        List<String> accessList = new ArrayList<>();
        accessList.add("Universal Access");
        accessList.add("Personal Access");

        Spinner accessSpinner = mView.findViewById(R.id.accessspinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                mView.getContext(), android.R.layout.simple_list_item_1, accessList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accessSpinner.setAdapter(spinnerAdapter);

        builder.setMessage("Create new Room")
                .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String roomName = ((EditText) mView.findViewById(R.id.roomname)).getText().toString();
                        RoomModel newModel = new RoomModel(roomName);

                        // Personal Access Selected
                        if (accessSpinner.getSelectedItemPosition() == 1) {
                            List<UserAccessModel> accessList = new ArrayList<>();
                            UserAccessModel userAccessModel = new UserAccessModel(CONSTANTS.CURRENT_USER_ID);
                            accessList.add(userAccessModel);
                            newModel.setUserAccess(accessList);
                        }

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

