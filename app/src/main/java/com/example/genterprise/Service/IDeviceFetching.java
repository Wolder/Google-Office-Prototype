package com.example.genterprise.Service;

import android.content.Context;

public interface IDeviceFetching extends Runnable {

    void getJsonObjFromServer(Context context, String input);
    String getIP();
    Integer getPort();
    void run();

}
