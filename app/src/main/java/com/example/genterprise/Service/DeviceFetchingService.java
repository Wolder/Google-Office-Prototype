package com.example.genterprise.Service;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DeviceFetchingService {

    private static final String TAG = "DeviceFetchingImpl";
    private String ip = "10.0.2.2";
    private int port = 8080;
    private List<String> jsonResponse = new ArrayList<>();
    private Context context;
    private Socket socket;
    private Thread t;


    public DeviceFetchingService() {
        this.ip = ip;
        this.port = port;
        this.context = context;
    }

    public String getIP() {
        return this.ip;
    }

    public int getPort() {
        return this.port;
    }


    public void connect() {
        t = new Thread("Data Fetching"){
            @Override
            public void run() {
                try {
                    socket = new Socket(ip, port);
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF("GET:JSON:");

                    //read input stream
                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                    InputStreamReader inputStreamReader = new InputStreamReader(dataInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


                    //print the input to the application screen
                    Log.d(TAG, "Response!" + bufferedReader.toString());
                    String resp;
                    //while ((resp = br.readLine()) != null) {
                    //    Log.d(TAG, resp);
                    //}

                    dataInputStream.close();
                    socket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }


}
