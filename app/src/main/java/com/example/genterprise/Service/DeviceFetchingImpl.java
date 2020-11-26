package com.example.genterprise.Service;

import android.content.Context;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class DeviceFetchingImpl implements IDeviceFetching  {

    private static final String TAG = "DeviceFetchingImpl";
    private String ip = "10.0.2.2";
    private int port = 8080;
    private Socket socket;
    private OutputStreamWriter oWriter;
    private Context context;
    private Thread t;


    @Override
    public String getJsonObjFromServer(String input) {
        Log.i(TAG, "getJsonObjFromServer: " + input);
        try {
            oWriter = new OutputStreamWriter(context.openFileOutput("models.json", Context.MODE_PRIVATE));
            oWriter.append(input);
            oWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public String getIP() {
        return this.ip;
    }

    @Override
    public Integer getPort() {
        return this.port;
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
