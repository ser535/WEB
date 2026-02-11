package com.example.demo.model;


import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.demo.model.Point.*;



@Entity @Data
@Table(name = "points")
@NamedQueries({
        @NamedQuery(name = FIND_BY_LOGIN, query = "SELECT p FROM Point p WHERE p.login = :login"),
        @NamedQuery(name = FIND_ALL, query = "SELECT p FROM Point p"),
        @NamedQuery(name = DELETE_ALL_BY_LOGIN, query = "DELETE FROM Point p WHERE p.login = :login")
})
public class Point implements Serializable{


    public static final String FIND_ALL = "Point.findAll";
    public static final String FIND_BY_LOGIN = "Point.findByLogin";
    public static final String DELETE_ALL_BY_LOGIN = "Point.deleteAll";

    @Id
    @Column(nullable = false, unique = true, name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Point(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.time = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date(System.currentTimeMillis()));
        checkResult(x, y, r);
    }


    @Column @NotNull @Min(value = -10) @Max(value = 10) private double x;

    @Column @NotNull @Min(value = -10) @Max(value = 10) private double y;

    @Column @NotNull @Min(value = -3) @Max(value = 5) private double r;

    @Column(nullable = false)
    private boolean result;

    @Column(name = "currenttime")
    private String time;

    @Column(name = "owner")
    private String login;


    public Point() {

    }

    private void checkResult(double x, double y, double r) {
        double R = Math.abs(r);
        this.result = (R > 0) && (inRectangle(x, y, R) || inTriangle(x, y, R) || inQuarterCircle(x, y, R));
    }

    private boolean inRectangle(double x, double y, double R) {
        return x >= 0 && y >= 0 && x <= R / 2.0 && y <= R;
    }

    private boolean inTriangle(double x, double y, double R) {
        return x <= 0 && y <= 0
                && x >= -R / 2.0
                && y >= -R / 2.0
                && y >= (-x - R / 2.0);
    }

    private boolean inQuarterCircle(double x, double y, double R) {
        return x >= 0 && y <= 0 && (x * x + y * y <= R * R);
    }

}
