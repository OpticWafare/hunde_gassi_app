package com.github.opticwafare.hunde_gassi_app;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public interface UpdateLocationTimerListener {

    /**
     *
     * @param points
     * @param newPoint Neuer Punkt.
     *                 Wenn kein neuer Punkt hinzugefügt wurde (z.B. beim Löschen eines Punktes) ist newPoint = null
     */
    void pointsUpdated(ArrayList<LatLng> points, LatLng newPoint);

}
