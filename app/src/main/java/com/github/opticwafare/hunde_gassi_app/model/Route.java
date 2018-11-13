package com.github.opticwafare.hunde_gassi_app.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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

    @Override
    public String toString() {
        Date startTime = new Date(getStartTime());

        long timeDifference = getEndTime() - getStartTime();
        Date timeDiff = new Date(timeDifference);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setCalendar(Calendar.getInstance());
        dateFormat.setTimeZone(Calendar.getInstance().getTimeZone());
        String string = dateFormat.format(startTime);

        SimpleDateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss");
        dateFormat1.setTimeZone(TimeZone.getTimeZone("Etc/GMT"));
        string += " ("+dateFormat1.format(timeDifference) + ")";
        return string;
    }
}
