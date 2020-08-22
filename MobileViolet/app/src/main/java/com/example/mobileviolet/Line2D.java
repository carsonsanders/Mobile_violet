package com.example.mobileviolet;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;

/**
 * Created by hayleycarpenter on 2017-04-25.
 */

public class Line2D
{
    public Line2D(Point2D start, Point2D end)
    {
        x1 = start.getX();
        y1 = start.getY();
        x2 = end.getX();
        y2 = end.getY();
    }

    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[]{15, 15}, 0));
        Path path = new Path();
        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
        canvas.drawPath(path, paint);
    }

    public float getX1(){return x1;}
    public float getY1(){return y1;}
    public float getX2(){return x2;}
    public float getY2(){return y2;}
    public Point2D getP1() {return new Point2D(x1, y1); }
    public Point2D getP2() {return new Point2D(x2, y2);}

    private float x1;
    private float y1;
    private float x2;
    private float y2;
}