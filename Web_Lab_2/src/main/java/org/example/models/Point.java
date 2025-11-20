package org.example.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Point {
    private boolean isHit;
    private double x;
    private double y;
    private double r;
    private double workTime;
    private String time;

    public Point(double x, double y, double r, boolean isHit, long workTime) {
        setX(x);
        setY(y);
        setR(r);
        setIsHit(isHit);
        setWorkTime(workTime);
        setTime();
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }

    public void setWorkTime(long workTime) {
        this.workTime = (double) (System.nanoTime() - workTime) / 10000000;
    }

    public void setTime() {
        this.time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getR() {
        return this.r;
    }

    public boolean getIsHit() {
        return this.isHit;
    }

    public double getWorktime() {
        return this.workTime;
    }

    public String getTime() {
        return this.time;
    }
}