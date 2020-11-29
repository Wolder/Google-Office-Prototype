package com.example.genterprise.Service;

import android.content.Context;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DeviceFetchingService {

    private static final String TAG = "DeviceFetchingImpl";
    private String ip = "http://10.0.2.2:5000/";
    private int port = 5000;
    private List<String> jsonResponse = new ArrayList<>();
    private Socket socket;

    public DeviceFetchingService() {
        this.ip = ip;
        this.port = port;
    }

    public void connect(){
        try {
            socket = IO.socket(ip);
            httpGET();
        } catch (URISyntaxException e) {
            Log.d(TAG, "connect: Error on connection!");
            e.printStackTrace();
        }
    }

    public String getIP() {
        return this.ip;
    }

    public Integer getPort() {
        return this.port;
    }

    public void httpGET(){
        boolean dataSent = false;
        socket.connect();
        Log.d(TAG, "httpGET: Connecting to server...");
        if(socket.connected()){
            Log.d(TAG, "httpGET: Connected...");
            emitData();
            Log.d(TAG, "httpGET: Data emitted...");
            dataSent = true;
        }
        if (dataSent){
            socket.on("new message", onResponse);
            socket.connect();
        }
    }

    private Emitter.Listener onResponse = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String usr;
                    String msg;
                    try {
                        usr = data.getString("name");
                        msg = data.getString("message");
                    } catch (JSONException e){
                        return;
                    }
                    Log.d(TAG, "Results from server: " + usr + " : " + msg);
                }
            };
        }
    };

    private void emitData(){
        String msg = "GET:JSON:";
        socket.emit("new message", msg);
    }

/*    public void initWorking(){
        executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 20; i++) {
            executor.execute(new DeviceFetchingService());
        }
    }*/


}
