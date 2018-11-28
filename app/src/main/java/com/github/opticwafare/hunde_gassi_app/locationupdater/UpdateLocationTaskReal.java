package com.github.opticwafare.hunde_gassi_app.locationupdater;

import android.location.Location;

import android.location.LocationListener;
import android.os.Bundle;
import android.renderscript.ScriptIntrinsicYuvToRGB;

import com.github.opticwafare.hunde_gassi_app.tabs.MapsTab;
import com.google.android.gms.maps.model.LatLng;

/**
 * Task welcher immer die aktuelle Position des Handys zurückgibt
 */
public class UpdateLocationTaskReal extends UpdateLocationTask implements LocationListener {

    private MapsTab mapsTab;

    public UpdateLocationTaskReal(MapsTab mapsTab) {
        this.mapsTab = mapsTab;
    }

    @Override
    public void run() {

        //mapsTab.getDeviceLocation();
        counter++;
    }

    public MapsTab getMapsTab() {
        return mapsTab;
    }

    public void setMapsTab(MapsTab mapsTab) {
        this.mapsTab = mapsTab;
    }

    /*@Override
    public void currentLocationChanged(Location currentLocation) {

        if(currentLocation != null) {
            LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

            if(counter < 2) {
                mapsTab.moveCamera(currentLatLng, MapsTab.DEFAULT_ZOOM);
            }

            getTimer().addnewPoint(currentLatLng);
        }
        else {
            System.out.println("Current location was null");
        }
    }*/

    @Override
    public void onLocationChanged(Location location) {

        System.out.println("UpdateLocationTaskReal - onLocationChanged");
        if(location != null) {
            LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

            if(counter < 2) {
                mapsTab.moveCamera(currentLatLng, MapsTab.DEFAULT_ZOOM);
            }

            getTimer().addnewPoint(currentLatLng);
        }
        else {
            System.out.println("Current location was null");
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
