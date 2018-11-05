package com.github.opticwafare.hunde_gassi_app.locationupdater;

import java.util.TimerTask;

/**
 * TimerTask für einen UpdateLocationTimer
 * Die Sub-Klassen dieses Tasks implementieren die run-Methode,
 * welche immer wieder eine neue Location finden
 */
public abstract class UpdateLocationTask extends TimerTask {

    private UpdateLocationTimer timer;
    protected int counter;

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
