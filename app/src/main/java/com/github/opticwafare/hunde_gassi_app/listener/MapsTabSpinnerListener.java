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

        Route route = mapsTab.getWalkedRoutes()[position];
        Polyline polyline = mapsTab.getmMap().addPolyline(
                new PolylineOptions()
                .clickable(true)
        );

        List<LatLng> latLngList = LatLngTools.convertPointsToLatLngs(route.getPoints());
        polyline.setPoints(latLngList);

        float lengthOfRoute = calculateDistance(polyline);
        System.out.println("Length of route: " + lengthOfRoute);
        mapsTab.setTextViewInfo1Text("Strecke: "+formatDistance(lengthOfRoute)+"m");

        if(mapsTab.getCurrentRoute() != null) {
            mapsTab.getCurrentRoute().setPoints(new ArrayList<LatLng>());
        }
        mapsTab.setCurrentRoute(polyline);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        System.out.println("MapsTab Spinner: nothing selected");
    }

    public float calculateDistance(Polyline polyline) {
        float [] result = new float[1];
        float result1 = 0;
        for(int i = 0; i < polyline.getPoints().size()-1; i++)
        {
            LatLng latLng = polyline.getPoints().get(i);
            LatLng latLng1 = polyline.getPoints().get(i+1);
            Location.distanceBetween(latLng.latitude, latLng.longitude, latLng1.latitude, latLng1.longitude, result);
            result1 = result1 + result[0];
        }
        return result1;
    }

    public String formatDistance(float distance) {
        return String.format("%,.0f", distance);
    }
}
