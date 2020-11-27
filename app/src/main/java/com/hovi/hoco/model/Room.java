package com.hovi.hoco.model;

import java.util.ArrayList;

public class Room {
    private String name;
    private ArrayList<Device> devices;

    public Room(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    public void setDevices(ArrayList<Device> devices) {
        this.devices = devices;
    }
}
