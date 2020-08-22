package com.example.mobileviolet;

/**
 * Created by hayleycarpenter on 2017-04-25.
 */

import java.io.Serializable;

/**
 This class describes a direction in the 2D plane.
 A direction is a vector of length 1 with an angle between 0
 (inclusive) and 360 degrees (exclusive). There is also
 a degenerate direction of length 0.
 */
public class Direction implements Serializable
{
    /**
     Constructs a direction (normalized to length 1).
     @param dx the x-value of the direction
     @param dy the corresponding y-value of the direction
     */
    public Direction(double dx, double dy)
    {
        x = dx;
        y = dy;
        double length = Math.sqrt(x * x + y * y);
        if (length == 0) return;
        x = x / length;
        y = y / length;
    }

    /**
     Constructs a direction between two points
     @param p the starting point
     @param q the ending point
     */
    public Direction(Point2D p, Point2D q)
    {
        this(q.getX() - p.getX(),
                q.getY() - p.getY());
    }

    /**
     Turns this direction by an angle.
     @param angle the angle in degrees
     */
    public Direction turn(double angle)
    {
        double a = Math.toRadians(angle);
        return new Direction(
                x * Math.cos(a) - y * Math.sin(a),
                x * Math.sin(a) + y * Math.cos(a));
    }

    /**
     Gets the x-component of this direction
     @return the x-component (between -1 and 1)
     */
    public double getX()
    {
        return x;
    }

    /**
     Gets the y-component of this direction
     @return the y-component (between -1 and 1)
     */
    public double getY()
    {
        return y;
    }

    private double x;
    private double y;

    public static final Direction NORTH = new Direction(0, -1);
    public static final Direction SOUTH = new Direction(0, 1);
    public static final Direction EAST = new Direction(1, 0);
    public static final Direction WEST = new Direction(-1, 0);
}
