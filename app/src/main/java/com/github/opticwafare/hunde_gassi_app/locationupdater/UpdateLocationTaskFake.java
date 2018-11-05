package com.github.opticwafare.hunde_gassi_app.locationupdater;

import com.google.android.gms.maps.model.LatLng;

public class UpdateLocationTaskFake extends UpdateLocationTask {

    private LatLng startPoint;
    private double latInterval;
    private double lngInterval;

    public UpdateLocationTaskFake(LatLng startPoint, double latInterval, double lngInterval) {
        this.startPoint = startPoint;
        this.latInterval = latInterval;
        this.lngInterval = lngInterval;
    }

    @Override
    public void run() {

        double newLat = startPoint.latitude + (counter * latInterval);
        double newLng = startPoint.longitude + (counter * lngInterval);
        LatLng newPoint = new LatLng(newLat, newLng);

        getTimer().addnewPoint(newPoint);

        counter++;
    }

    public LatLng getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(LatLng startPoint) {
        this.startPoint = startPoint;
    }

    public double getLatInterval() {
        return latInterval;
    }

    public void setLatInterval(double latInterval) {
        this.latInterval = latInterval;
    }

    public double getLngInterval() {
        return lngInterval;
    }

    public void setLngInterval(double lngInterval) {
        this.lngInterval = lngInterval;
    }
}
