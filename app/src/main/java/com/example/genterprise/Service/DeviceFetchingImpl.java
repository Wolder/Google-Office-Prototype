package com.example.genterprise.Service;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DeviceFetchingImpl extends Thread implements IDeviceFetching  {

    private static final String TAG = "DeviceFetchingImpl";
    private String ip = "10.0.2.2";
    private int port = 8080;
    private Socket socket;


    @Override
    public String getJsonObjFromServer(String input) {
        Log.i(TAG, "getJsonObjFromServer: " + input);
        return input;
    }

    @Override
    public void connectToServer(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            Log.d(TAG, "Init server...");
            if(socket.isBound()){
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeChar(1);

                DataInputStream input = new DataInputStream(socket.getInputStream());

                getJsonObjFromServer(input.readUTF());
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        connectToServer(ip, port);
    }

    public DeviceFetchingImpl() {
        this.ip = ip;
        this.port = port;
    }
}
