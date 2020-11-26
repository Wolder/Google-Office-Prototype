package com.example.genterprise.Service;

public interface IDeviceFetching extends Runnable {

    String getJsonObjFromServer(String input);
    void connectToServer(String ip, int port);
    String getIP();
    Integer getPort();
    void run();

}
