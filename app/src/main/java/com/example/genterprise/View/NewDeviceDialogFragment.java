package com.example.genterprise.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;

import com.example.genterprise.CONSTANTS;
import com.example.genterprise.Controller.DataController;
import com.example.genterprise.Model.Devices;
import com.example.genterprise.Model.RoomModel;
import com.example.genterprise.Model.UserAccessModel;
import com.example.genterprise.R;

import java.util.ArrayList;
import java.util.List;

public class NewDeviceDialogFragment extends DialogFragment {
    int floorI;
    int roomI;

    public NewDeviceDialogFragment(int floorI, int roomI) {
        this.floorI = floorI;
        this.roomI = roomI;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View mView = inflater.inflate(R.layout.popup_window_new_device, null);
        builder.setView(mView);

        List<String> accessList = new ArrayList<>();
        accessList.add("light");
        accessList.add("lux");
        accessList.add("blinds");

        EditText typeName =  mView.findViewById(R.id.deviceid);
        Spinner typespinner = mView.findViewById(R.id.typespinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                mView.getContext(), android.R.layout.simple_list_item_1, accessList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typespinner.setAdapter(spinnerAdapter);

        builder.setMessage("Create new Device")
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String uid = typeName.getText().toString();
                        String type = typespinner.getSelectedItem().toString();

                        Devices newModel = new Devices(type, 0, uid);

                        DataController.getInstance().createNewDevice(newModel, floorI, roomI);
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
