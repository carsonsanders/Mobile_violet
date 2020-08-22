package com.example.mobileviolet;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.shapes.RectShape;

import java.io.Serializable;

import static java.lang.Math.round;

/**
 * Created by ginge_000 on 4/24/2017.
 */

public class Rectangle2D implements Serializable {

    // No args constructor for AbstractEdge
    public Rectangle2D() {
        this.x = 0;
        this.y = 0;
        this.height = 0;
        this.width = 0;
    }

    public Rectangle2D(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public void setFrame(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getMaxX() {
        return x + width;
    }

    public double getMaxY() {
        return y + height;
    }

    public double getCenterX() {
        return x + width / 2;
    }

    public double getCenterY() {
        return y + height / 2;
    }

    public boolean contains(float x, float y) {
        //Rect rect = new Rect((int)round(getCenterX() - width), (int) round(getCenterY() - height), (int) round(getCenterX()+ width), (int)round(getCenterY() + height));
        float pointX = x;
        float pointY = y;
        return (pointX >= this.getX() && pointX <= this.getMaxX() && pointY >= this.getY() && pointY <= this.getMaxY());
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawRect(new Rect((int) x, (int) y, (int) (x + width), (int) (y + height)), paint);
    }

    public void add(Rectangle2D r) {
        double x1 = Math.min(getX(), r.getX());
        double x2 = Math.max(getMaxX(), r.getMaxX());
        double y1 = Math.min(getY(), r.getY());
        double y2 = Math.max(getMaxY(), r.getMaxY());
        setFrame(x1, y1, x2 - x1, y2 - y1);
    }


    private double x;
    private double y;
    private double height;
    private double width;
}