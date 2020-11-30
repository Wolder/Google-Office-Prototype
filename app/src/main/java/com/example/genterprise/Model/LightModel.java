package com.example.genterprise.Model;

import java.io.Serializable;

public class LightModel extends Devices implements Serializable {

    public LightModel(String type, double value, String ID) {
        super(type, value, ID);
    }
}
