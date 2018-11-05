package com.github.opticwafare.hunde_gassi_app.model;

public class Point {

    private int pointid;
    private double longitude;
    private double latitude;
    private int routeid;

    public Point(int pointid, double longitude, double latitude, int routeid) {
        this(longitude, latitude, routeid);
        this.pointid = pointid;
    }

    public Point(double longitude, double latitude, int routeid) {
        this(longitude, latitude);
        this.routeid = routeid;
    }

    public Point(double longitude, double latitude) {
        super();
        this.longitude = longitude;
        this.latitude = latitude;
    }


    public int getPointid() {
        return pointid;
    }

    public void setPointid(int pointid) {
        this.pointid = pointid;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getRouteid() {
        return routeid;
    }

    public void setRouteid(int routeid) {
        this.routeid = routeid;
    }
}
