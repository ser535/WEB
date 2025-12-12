package example;


import java.io.Serializable;
import java.util.ArrayList;


public class PointCheckerBean implements Serializable {



    public boolean validation(double x, double y, double r) {
        ArrayList<Double> forR = new ArrayList<>();
        forR.add(1d);
        forR.add(1.5d);
        forR.add(2d);
        forR.add(2.5d);
        forR.add(3d);

        return x >= -5 && x <= 5 && forR.contains(r) && y >= -5 && y <= 5;
    }

    public boolean checkPoint(double x, double y, double r) {
        return checkCircle(x, y, r) || checkRectangle(x, y, r) || checkTriangle(x, y, r);
    }

    public boolean checkRectangle(double x, double y, double r) {
        return x >= 0 && y <= 0 && x <= r/2 && y >= -r ;
    }

    public boolean checkCircle(double x, double y, double r) {
        return x <= 0 && y >= 0 && Math.sqrt(x*x + y*y) <= r/2;
    }

    public boolean checkTriangle(double x, double y, double r) {
        return x >= 0 && y >= 0 && x + y <= r/2;
    }

}