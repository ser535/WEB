package org.example.validator;

import java.util.LinkedList;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;
public class Validate {
    private final List<Double> xRange = new LinkedList<>();
    private final List<Double> rRange = new LinkedList<>();
    private String log = "all ok";
    public Validate(){
        xRange.add(-5.0);
        xRange.add(-4.0);
        xRange.add(-3.0);
        xRange.add(-2.0);
        xRange.add(-1.0);
        xRange.add(0.0);
        xRange.add(1.0);
        xRange.add(2.0);
        xRange.add(3.0);

        rRange.add(1.0);
        rRange.add(1.5);
        rRange.add(2.0);
        rRange.add(2.5);
        rRange.add(3.0);
    }
    public boolean checkForm(double x, double y, double r){
        return checkX(x) && checkY(y) && checkR(r);
    }
    public boolean checkMouse(double x, double y, double r){
        return checkXMouse(x) && checkYMouse(y) && checkRMouse(r);
    }

    public String getErr(){
        return log;
    }

    public boolean checkX(double x){
        if (xRange.contains(x)){
            return true;
        }
        log = "x must be selected";
        return false;
    }

    public boolean checkY(double y){
        if (-5 <= y && y <= 5){
            return true;
        }
        log = "Y value must be bigger than -5 and lower than 5";
        return false;
    }

    public boolean checkR(double r){
        if (rRange.contains(r)){
            return true;
        }
        log = "R must be selected";
        return false;
    }

    public boolean checkXMouse(double x){
        if (-5 <= x && x <= 5){
            return true;
        }
        log = "x must be selected";
        return false;
    }

    public boolean checkYMouse(double y){
        if (-5 <= y && y <= 5){
            return true;
        }
        log = "Y value must be bigger than -5 and lower than 5";
        return false;
    }

    public boolean checkRMouse(double r){
        if (rRange.contains(r)){
            return true;
        }
        log = "R must be selected";
        return false;
    }
}
