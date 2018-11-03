package com.github.opticwafare.hunde_gassi_app;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateLocationTimer extends Timer {

    private ArrayList<UpdateLocationTimerListener> listener;
    private ArrayList<LatLng> points;

    private Polyline polyline;

    public UpdateLocationTimer(Polyline polyline) {
        this();
        this.polyline = polyline;
    }

    public UpdateLocationTimer() {
        this.listener = new ArrayList<>();
        this.points = new ArrayList<>();
    }

    /** Wird von UpdateLocationTask ausgeführt */
    public void addnewPoint(LatLng newPoint) {
        this.points.add(newPoint);
        // TODO vl als eigenen listener auslagern
        if(polyline != null) {
            polyline.setPoints(this.points);
        }
        notifiyListener(this.points, newPoint);
    }

    public void schedule(UpdateLocationTask task, long delay, long period) {
        task.setTimer(this);
        super.schedule(task, delay, period);
    }

    public ArrayList<UpdateLocationTimerListener> getListener() {
        return listener;
    }

    public void addListener(UpdateLocationTimerListener listener) {
        this.listener.add(listener);
    }

    public void removeListener(UpdateLocationTimerListener listener) {
        this.listener.remove(listener);
    }

    public void setListener(ArrayList<UpdateLocationTimerListener> listener) {
        this.listener = listener;
    }

    private void notifiyListener(ArrayList<LatLng> points, LatLng newPoint) {
        for(UpdateLocationTimerListener listener : this.listener) {
            listener.pointsUpdated(points, newPoint);
        }
    }

    public ArrayList<LatLng> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<LatLng> points) {
        this.points = points;
    }
}
