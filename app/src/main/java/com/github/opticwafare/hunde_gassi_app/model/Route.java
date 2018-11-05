package com.github.opticwafare.hunde_gassi_app.model;

import java.sql.Date;
import java.util.ArrayList;

public class Route {

    private int routeid;
    private long startTime;
    private long endTime;
    private int userid;
    ArrayList<Point> points;

    public Route(int routeid, long startTime, long endTime, int userid) {
        this(startTime, endTime, userid);
        this.routeid = routeid;
    }

    public Route(long startTime, long endTime, int userid) {
        super();
        this.startTime = startTime;
        this.endTime = endTime;
        this.userid = userid;
    }

    public int getRouteid() {
        return routeid;
    }

    public void setRouteid(int routeid) {
        this.routeid = routeid;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }
}
