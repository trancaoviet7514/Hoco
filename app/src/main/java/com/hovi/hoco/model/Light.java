package com.hovi.hoco.model;

public class Light implements Device{
    private String name;
    private boolean state;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getState() {
        if(state)
            return 1;
        return 0;
    }
}
