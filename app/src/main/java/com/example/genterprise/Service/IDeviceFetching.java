package com.example.genterprise.Service;

public interface IDeviceFetching {

    String getJsonObjFromServer(String input);
    void connectToServer(String ip, int port);
}
