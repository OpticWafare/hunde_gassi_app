package com.github.opticwafare.hunde_gassi_app.listener;

import android.view.View;

import com.github.opticwafare.hunde_gassi_app.locationupdater.PolyLineUpdater;
import com.github.opticwafare.hunde_gassi_app.locationupdater.UpdateLocationTask;
import com.github.opticwafare.hunde_gassi_app.locationupdater.UpdateLocationTaskFake;
import com.github.opticwafare.hunde_gassi_app.locationupdater.UpdateLocationTimer;
import com.github.opticwafare.hunde_gassi_app.model.LatLngTools;
import com.github.opticwafare.hunde_gassi_app.model.Point;
import com.github.opticwafare.hunde_gassi_app.model.Route;
import com.github.opticwafare.hunde_gassi_app.servlettasks.GetMyRoutesTask;
import com.github.opticwafare.hunde_gassi_app.servlettasks.SaveRouteTask;
import com.github.opticwafare.hunde_gassi_app.servlettasks.SendNotificationTask;
import com.github.opticwafare.hunde_gassi_app.tabs.MapsTab;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NewRouteButtonListener implements View.OnClickListener {

    private MapsTab mapsTab;

    public NewRouteButtonListener(MapsTab mapsTab) {
        this.mapsTab = mapsTab;
    }

    @Override
    public void onClick(View v) {

        Calendar calendar = Calendar.getInstance();

        /**
         * 0 = Keine Route aktiv
         * 1 = Route wird aufgezeichnet
         * 2 =
         */

        switch (mapsTab.getMapStatus()) {
            // Neue Route starten
            case 0:
                mapsTab.getSpinner().setEnabled(true);

                mapsTab.getBtnNewRoute().setText("Route stoppen");
                mapsTab.setMapStatus(1);

                Date currentDate = calendar.getTime();
                mapsTab.setCurrentRouteStartTime(currentDate);

                mapsTab.getSpinner().setEnabled(false);

                LatLng startPoint = new LatLng(-35.016, 143.321);

                // Add polylines and polygons to the map. This section shows just
                // a single polyline. Read the rest of the tutorial to learn more.
                Polyline currentRoute = mapsTab.getmMap().addPolyline(new PolylineOptions()
                        .clickable(true)
                        .add(startPoint));

                UpdateLocationTimer timer = new UpdateLocationTimer();
                mapsTab.setCurrentUpdateLocationTimer(timer);

                PolyLineUpdater polyLineUpdater = new PolyLineUpdater(currentRoute, mapsTab.getMainActivity());
                timer.addListener(polyLineUpdater);

                UpdateLocationTask timerTask = new UpdateLocationTaskFake(startPoint, 0.5, 0.5);
                timer.schedule(timerTask, TimeUnit.SECONDS.toMillis(3), TimeUnit.SECONDS.toMillis(3));

                mapsTab.setCurrentRoute(currentRoute);

                break;

            // Aufzeichnen der aktuellen Route stoppen
            case 1:
                mapsTab.getBtnNewRoute().setText("Neue Route starten");
                mapsTab.setMapStatus(0);

                mapsTab.getSpinner().setEnabled(true);

                Date endTime = calendar.getTime();

                if(mapsTab.getCurrentUpdateLocationTimer() != null) {
                    mapsTab.getCurrentUpdateLocationTimer().cancel();
                }

                Route route = new Route(mapsTab.getCurrentRouteStartTime().getTime(), endTime.getTime(), 1); // TODO

                List<LatLng> latLngList = mapsTab.getCurrentRoute().getPoints();
                ArrayList<Point> points = LatLngTools.convertLatLngsToPoints(latLngList);

                route.setPoints(points);

                SaveRouteTask saveRouteTask = new SaveRouteTask();
                saveRouteTask.setRoute(route);
                saveRouteTask.execute("");

                GetMyRoutesTask getMyRoutesTask = new GetMyRoutesTask();
                getMyRoutesTask.setMapsTab(mapsTab);
                getMyRoutesTask.execute("");
                break;
        }
    }
}