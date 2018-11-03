package com.github.opticwafare.hunde_gassi_app;

import com.google.android.gms.maps.model.LatLng;

import java.util.TimerTask;

public abstract class UpdateLocationTask extends TimerTask {

    UpdateLocationTimer timer;
    int counter;

    public UpdateLocationTask() {
        counter = 0;
    }

    @Override
    public abstract void run();

    public UpdateLocationTimer getTimer() {
        return timer;
    }

    public void setTimer(UpdateLocationTimer timer) {
        this.timer = timer;
    }

    public int getCounter() {
        return counter;
    }
}
