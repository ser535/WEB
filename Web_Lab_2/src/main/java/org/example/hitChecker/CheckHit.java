package org.example.hitChecker;

public class CheckHit {

    public boolean hit(double x, double y, double r) {
        System.out.println("x: " + x + " y: " + y + " r: " + r);
        return checkCircle(x, y, r) || checkRectangle(x, y, r) || checkTriangle(x, y, r);
    }

    public boolean checkRectangle(double x, double y, double r) {
        return x >= -r && x <= 0 && y >= -r/2 && y <= 0;
    }

    public boolean checkCircle(double x, double y, double r) {
        return x <= 0 && y >= 0 && Math.sqrt(x*x + y*y) <= r;
    }

    public boolean checkTriangle(double x, double y, double r) {
        return x >= 0 && y >= 0 && x + y <= r/2;
    }

}
