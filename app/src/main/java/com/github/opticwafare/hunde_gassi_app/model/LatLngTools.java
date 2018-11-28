package com.github.opticwafare.hunde_gassi_app.model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;

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


    public static float calculateDistance(Polyline polyline) {
        return calculateDistance(polyline.getPoints());
    }

    public static float calculateDistance(List<LatLng> points) {
        float [] result = new float[1];
        float result1 = 0;
        for(int i = 0; i < points.size()-1; i++)
        {
            LatLng latLng = points.get(i);
            LatLng latLng1 = points.get(i+1);
            Location.distanceBetween(latLng.latitude, latLng.longitude, latLng1.latitude, latLng1.longitude, result);
            result1 = result1 + result[0];
        }
        return result1;
    }

    public static String formatDistance(float distance) {
        return String.format("%,.0f", distance);
    }

    public static String distanceToLabelString(float distance) {
        return "Strecke: "+formatDistance(distance)+"m";
    }
}