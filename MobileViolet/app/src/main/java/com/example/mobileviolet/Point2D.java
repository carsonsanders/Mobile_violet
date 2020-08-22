package com.example.mobileviolet;

import java.io.Serializable;

/**
 * Created by phoenixnguyen on 4/23/17.
 */

public class Point2D implements Serializable{
    private static final long serialVersionUID = 13124124;
    private double x;
    private double y;

    public Point2D(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return (int)x;
    }

    public int getY()
    {
        return (int)y;
    }

    // Needed for grid
    public void setLocation(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double distance(Point2D that)
    {
        double dx = this.x - that.x;
        double dy = this.y - that.y;
        return dx + dy;
    }
}