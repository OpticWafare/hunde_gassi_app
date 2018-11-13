package com.github.opticwafare.hunde_gassi_app;

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

        Route route = mapsTab.getWalkedRoutes()[position];
        Polyline polyline = mapsTab.getmMap().addPolyline(
                new PolylineOptions()
                .clickable(true)
        );

        List<LatLng> latLngList = LatLngTools.convertPointsToLatLngs(route.getPoints());
        polyline.setPoints(latLngList);

        if(mapsTab.getCurrentRoute() != null) {
            mapsTab.getCurrentRoute().setPoints(new ArrayList<LatLng>());
        }
        mapsTab.setCurrentRoute(polyline);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        System.out.println("MapsTab Spinner: nothing selected");
    }
}
