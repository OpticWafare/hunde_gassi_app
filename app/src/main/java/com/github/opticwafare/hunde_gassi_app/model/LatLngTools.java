package com.github.opticwafare.hunde_gassi_app.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class LatLngTools {

    public static Point convertLatLngToPoint(LatLng latLng) {
        Point p = new Point(latLng.longitude, latLng.latitude);
        return p;
    }

    public static ArrayList<Point> convertLatLngsToPoints(List<LatLng> latLngList) {
        ArrayList<Point> points = new ArrayList<>();
        Point p;
        for(int i = 0;i < latLngList.size(); i++) {
            p = convertLatLngToPoint(latLngList.get(i));
            points.add(p);
        }
        return points;
    }

    public static LatLng convertPointToLatLng(Point point) {
        LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
        return latLng;
    }

    public static List<LatLng> convertPointsToLatLngs(List<Point> points) {
        ArrayList<LatLng> latLngs = new ArrayList<>();
        LatLng latLng;
        for(int i = 0; i < points.size(); i++) {
            latLng = convertPointToLatLng(points.get(i));
            latLngs.add(latLng);
        }
        return latLngs;
    }

}