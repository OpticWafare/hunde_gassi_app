package com.github.opticwafare.hunde_gassi_app.listener;

import android.location.Location;
import android.view.View;
import android.widget.AdapterView;

import com.github.opticwafare.hunde_gassi_app.model.LatLngTools;
import com.github.opticwafare.hunde_gassi_app.model.Route;
import com.github.opticwafare.hunde_gassi_app.tabs.MapsTab;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsTabSpinnerListener implements AdapterView.OnItemSelectedListener {

    private MapsTab mapsTab;

    public MapsTabSpinnerListener(MapsTab mapsTab) {
        this.mapsTab = mapsTab;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        System.out.println("Selected mapsTab Spinner item: " + position);

        // Alte Polylines entfernen
        //mapsTab.getmMap().clear();
        if(mapsTab.getCurrentRoute() != null) {
            mapsTab.getCurrentRoute().remove();
            mapsTab.setCurrentRoute(null);
        }

        if(position == 0) {
            System.out.println("MapsTab Spinner position is 0 -> no route shown");
            mapsTab.setTextViewInfo1Text("");
            return;
        }

        position = position - 1;
        Route route = mapsTab.getWalkedRoutes()[position];

        // Neue (vorerst leere) Polyline zur Karte hinzufügen
        Polyline polyline = mapsTab.getmMap().addPolyline(
                new PolylineOptions()
                .clickable(true)
        );

        List<LatLng> latLngList = LatLngTools.convertPointsToLatLngs(route.getPoints());
        polyline.setPoints(latLngList);

        float lengthOfRoute = LatLngTools.calculateDistance(polyline);
        System.out.println("Length of route: " + lengthOfRoute);
        mapsTab.setTextViewInfo1Text(LatLngTools.distanceToLabelString(lengthOfRoute));

        /*if(mapsTab.getCurrentRoute() != null) {
            mapsTab.getCurrentRoute().setPoints(new ArrayList<LatLng>());
        }*/
        mapsTab.setCurrentRoute(polyline);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        System.out.println("MapsTab Spinner: nothing selected");
    }
}
