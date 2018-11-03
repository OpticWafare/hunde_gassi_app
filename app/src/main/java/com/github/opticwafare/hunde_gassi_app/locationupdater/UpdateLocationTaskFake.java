package com.github.opticwafare.hunde_gassi_app;

import com.google.android.gms.maps.model.LatLng;

public class UpdateLocationTaskFake extends UpdateLocationTask {

    private LatLng startPoint;
    private double latInterval;
    private double lngInterval;

    @Override
    public void run() {

        double newLat = startPoint.latitude + (counter * latInterval);
        double newLng = startPoint.longitude + (counter * lngInterval);
        LatLng newPoint = new LatLng(newLat, newLng);

        getTimer().addnewPoint(newPoint);

        counter++;
    }
}
