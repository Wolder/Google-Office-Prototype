package com.example.genterprise.Service;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.example.genterprise.Root;
import com.google.gson.Gson;


public class DeviceFetchingService {

    private static final String TAG = "DeviceFetchingImpl";
    private String ip = "10.0.2.2";
    private int port = 8080;
    private List<String> jsonResponse = new ArrayList<>();
    private Socket socket;

    public DeviceFetchingService() {
        this.ip = ip;
        this.port = port;
    }

    public String getIP() {
        return this.ip;
    }

    public int getPort() {
        return this.port;
    }


    public void connect() {
         new Thread(new Runnable() {
            @Override
            public void run() {
                String response = serverCall("GET:JSON:");
                Log.d(TAG, "run: " + response);

                // TODO -> TEST IMPLEMENTATION
                Root data = new Gson().fromJson(response, Root.class);

                Log.d(TAG, "run: " + data.rooms.get(0).roomname);
            }
        }).start();
    }

    public String serverCall(String input) {
        String responce = null;
        try {
            socket = new Socket(InetAddress.getByName(ip), port);
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            printWriter.println(input);
            printWriter.flush();

            InputStream inputStream = socket.getInputStream();
            int lockSeconds = 10000; //10 millis

            long lockThreadCheckpoint = System.currentTimeMillis();
            int availableBytes = inputStream.available();
            while (availableBytes <= 0 && (System.currentTimeMillis() < lockThreadCheckpoint + lockSeconds)) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                availableBytes = inputStream.available();
            }

            // Read response
            byte[] buffer = new byte[availableBytes];
            inputStream.read(buffer, 0, availableBytes);
            responce = new String(buffer);

            // Close connections
            printWriter.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responce;
    }
}


