package com.github.opticwafare.hunde_gassi_app.locationupdater;

import android.app.Activity;
import android.widget.TextView;

import com.github.opticwafare.hunde_gassi_app.model.LatLngTools;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class DistanceLabelUpdater implements UpdateLocationTimerListener {

    private TextView textViewDistance;
    private Activity activity;

    public DistanceLabelUpdater(TextView textViewDistance, Activity activity) {
        this.textViewDistance = textViewDistance;
        this.activity = activity;
    }

    @Override
    public void pointsUpdated(ArrayList<LatLng> points, LatLng newPoint) {

        final float distance = LatLngTools.calculateDistance(points);
        System.out.println("Current route distance: " + distance);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewDistance.setText(LatLngTools.distanceToLabelString(distance));
            }
        });
    }

    public TextView getTextViewDistance() {
        return textViewDistance;
    }

    public void setTextViewDistance(TextView textViewDistance) {
        this.textViewDistance = textViewDistance;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
