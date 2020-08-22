package com.example.mobileviolet;

/**
 * Created by ginge_000 on 4/28/2017.
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.io.Serializable;

/**
 This class defines arrowheads of various shapes.
 */
public class ArrowHead implements Serializable
{
    private ArrowHead() {}

    /**
     Draws the arrowhead.
     @param canvas the graphics context
     @param p a point on the axis of the arrow head
     @param q the end point of the arrow head
     */
    public void draw(Canvas canvas, Point2D p, Point2D q)
    {
        Path path = getPath(p, q);
        Paint paint = new Paint(Color.BLACK);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);


        //g2.fill(path);

        canvas.drawPath(path, paint);
    }

    /**
     Gets the path of the arrowhead
     @param p a point on the axis of the arrow head
     @param q the end point of the arrow head
     @return the path
     */
    public Path getPath(Point2D p, Point2D q)
    {
        Path path = new Path();
        if (this == NONE) return path;
        final double ARROW_ANGLE = Math.PI / 6;
        final double ARROW_LENGTH = 30;

        double dx = q.getX() - p.getX();
        double dy = q.getY() - p.getY();
        double angle = Math.atan2(dy, dx);
        double x1 = q.getX()
                - ARROW_LENGTH * Math.cos(angle + ARROW_ANGLE);
        double y1 = q.getY()
                - ARROW_LENGTH * Math.sin(angle + ARROW_ANGLE);
        double x2 = q.getX()
                - ARROW_LENGTH * Math.cos(angle - ARROW_ANGLE);
        double y2 = q.getY()
                - ARROW_LENGTH * Math.sin(angle - ARROW_ANGLE);

        path.moveTo((float)q.getX(), (float)q.getY());
        path.lineTo((float)x1, (float)y1);
        if (this == V)
        {
            path.moveTo((float)x2, (float)y2);
            path.lineTo((float)q.getX(), (float)q.getY());
        }
        else if (this == TRIANGLE || this == BLACK_TRIANGLE)
        {
            path.lineTo((float)x2, (float)y2);
            path.close();
        }
        else if (this == DIAMOND || this == BLACK_DIAMOND)
        {
            double x3 = x2 - ARROW_LENGTH * Math.cos(angle + ARROW_ANGLE);
            double y3 = y2 - ARROW_LENGTH * Math.sin(angle + ARROW_ANGLE);
            path.lineTo((float)x3, (float)y3);
            path.lineTo((float)x2, (float)y2);
            path.close();
        }
        return path;
    }

    public static final ArrowHead NONE = new ArrowHead();
    public static final ArrowHead TRIANGLE = new ArrowHead();
    public static final ArrowHead BLACK_TRIANGLE = new ArrowHead();
    public static final ArrowHead V = new ArrowHead();
    public static final ArrowHead HALF_V = new ArrowHead();
    public static final ArrowHead DIAMOND = new ArrowHead();
    public static final ArrowHead BLACK_DIAMOND = new ArrowHead();
}