package com.example.mobileviolet;

/**
 * Created by kitt3 on 2017-05-01.
 */
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 An edge that joins two call nodes.
 */
public class ReturnEdge extends SegmentedLineEdge
{
    public ReturnEdge()
    {
        setEndArrowHead(ArrowHead.V);
    }

    public ArrayList getPoints()
    {
        ArrayList a = new ArrayList();
        Node n = getEnd();
        Rectangle2D start = getStart().getBounds();
        Rectangle2D end = getEnd().getBounds();
        if (n instanceof PointNode) // show nicely in tool bar
        {
            a.add(new Point2D(end.getX(), end.getY()));
            a.add(new Point2D(start.getMaxX(), end.getY()));
        }
        else if (start.getCenterX() < end.getCenterX())
        {
            a.add(new Point2D(start.getMaxX(), start.getMaxY()));
            a.add(new Point2D(end.getX(), start.getMaxY()));
        }
        else
        {
            a.add(new Point2D(start.getX(), start.getMaxY()));
            a.add(new Point2D(end.getMaxX(), start.getMaxY()));
        }
        return a;
    }

    public Rectangle2D getBounds(Canvas canvas)
    {
        return super.getBounds(canvas);
    }

}


