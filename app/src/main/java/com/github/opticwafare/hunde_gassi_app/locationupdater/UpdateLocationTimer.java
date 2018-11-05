package com.github.opticwafare.hunde_gassi_app.locationupdater;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Timer zum Updaten der Location.
 * Verwendet einen UpdateLocationTask um die neue Location zu erhalten.
 *
 * Der UpdateLocationTask steht hierbei im Herzen dieser Klasse und liefert bei jedem Ablauf des Tasks eine neue Locations.
 * Die UpdateLocationTimer Klasse ist eigentlich nur ein Container für den UpdateLocationTask.
 * In UpdateLocationTimer werden alle Locations gespeichert und Listener über neue Locations informiert.
 *
 * Nach dem Observer-Pattern Prinzip werden eingetragene Observer über
 * die neuen Loctions benachrichtigt
 */
public class UpdateLocationTimer extends Timer {

    /** Alle eingetragenen Listener für diesen Timer */
    private ArrayList<UpdateLocationTimerListener> listener;
    private ArrayList<LatLng> points;

    public UpdateLocationTimer() {
        this.listener = new ArrayList<>();
        this.points = new ArrayList<>();
    }

    /** Wird von UpdateLocationTask ausgeführt */
    public void addnewPoint(LatLng newPoint) {
        this.points.add(newPoint);
        notifiyListener(this.points, newPoint);
    }

    /**
     * Stellt ein, dass der angegebene Task periodisch ausgeführt werden soll
     * @param task
     * @param delay
     * @param period
     */
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
