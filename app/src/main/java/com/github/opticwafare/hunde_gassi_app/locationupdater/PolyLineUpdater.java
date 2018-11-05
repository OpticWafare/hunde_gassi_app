package com.github.opticwafare.hunde_gassi_app.locationupdater;

import android.app.Activity;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;

/**
 * Wird verwendet um eine Polyline immer auf dem neuesten Stand zu halten.
 *
 */
public class PolyLineUpdater implements UpdateLocationTimerListener {

    private Polyline polyline;
    private Activity activity;

    public PolyLineUpdater(Polyline polyline, Activity activity) {
        this.polyline = polyline;
        this.activity = activity;
    }

    @Override
    public void pointsUpdated(final ArrayList<LatLng> points, LatLng newPoint) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                polyline.setPoints(points);
            }
        });
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
