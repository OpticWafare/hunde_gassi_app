package com.github.opticwafare.hunde_gassi_app;

import android.view.View;

import com.github.opticwafare.hunde_gassi_app.model.Point;
import com.github.opticwafare.hunde_gassi_app.model.Route;
import com.github.opticwafare.hunde_gassi_app.servlettasks.SaveRouteTask;
import com.github.opticwafare.hunde_gassi_app.servlettasks.SendNotificationTask;
import com.github.opticwafare.hunde_gassi_app.tabs.MapsTab;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewRouteButtonListener implements View.OnClickListener {

    private MapsTab mapsTab;

    public NewRouteButtonListener(MapsTab mapsTab) {
        this.mapsTab = mapsTab;
    }

    @Override
    public void onClick(View v) {

        Calendar calendar = Calendar.getInstance();
        switch (mapsTab.getMapStatus()) {
            // Neue Route starten
            case 0:
            case 2:
                mapsTab.getBtnNewRoute().setText("Route stoppen");
                mapsTab.setMapStatus(1);

                Date currentDate = calendar.getTime();
                mapsTab.setCurrentRouteStartTime(currentDate);

                break;

            // Aufzeichnen der aktuellen Route stoppen
            case 1:
                mapsTab.getBtnNewRoute().setText("Neue Route starten");
                mapsTab.setMapStatus(0);

                Date endTime = calendar.getTime();
                Route route = new Route(mapsTab.getCurrentRouteStartTime().getTime(), endTime.getTime(), 1); // TODO

                List<LatLng> latLngList = mapsTab.getCurrentRoute().getPoints();
                ArrayList<Point> points = convertLatLngsToPoints(latLngList);

                route.setPoints(points);

                SaveRouteTask saveRouteTask = new SaveRouteTask();
                saveRouteTask.setRoute(route);
                saveRouteTask.execute("");
                break;
        }

    }

    public Point convertLatLngToPoint(LatLng latLng) {
        Point p = new Point(latLng.longitude, latLng.latitude);
        return p;
    }

    public ArrayList<Point> convertLatLngsToPoints(List<LatLng> latLngList) {
        ArrayList<Point> points = new ArrayList<>();
        Point p;
        for(int i = 0;i < latLngList.size(); i++) {
            p = convertLatLngToPoint(latLngList.get(i));
            points.add(p);
        }
        return points;
    }
}
