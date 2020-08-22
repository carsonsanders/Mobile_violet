package com.example.mobileviolet;

/**
 * Created by kitt3 on 2017-05-01.
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;

/**
 A dotted line that connects a note to its attachment.
 */

public class NoteEdge extends ShapeEdge
{

    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[]{15, 15}, 0));
        getConnectionPoints().draw(canvas);
    }


    public Line2D getConnectionPoints()
    {
        Rectangle2D start = getStart().getBounds();
        Rectangle2D end = getEnd().getBounds();
        Direction d = new Direction(end.getCenterX() - start.getCenterX(), end.getCenterY() - start.getCenterY());
        return new Line2D(getStart().getConnectionPoint(d), getEnd().getConnectionPoint(d.turn(180)));
    }

    /**
     * Returns a combination of lines given the connection points and lines.
     * @return The line path.
     */
    @Override
    public Path getShape()
    {
        Path path = new Path();
        Line2D conn = getConnectionPoints();
        path.moveTo(conn.getX1(), conn.getY1());
        path.lineTo(conn.getX2(), conn.getY2());
        return path;
    }


    //private static Stroke DOTTED_STROKE = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0.0f,new float[] { 3.0f, 3.0f }, 0.0f);

}