package com.example.mobileviolet;

/**
 * Created by ginge_000 on 4/27/2017.
 */

import android.graphics.Canvas;
import java.util.ArrayList;

/**
 An edge that joins two call nodes.
 */

public class CallEdge extends SegmentedLineEdge
{
    private static final long serialVersionUID = 274792910;
    public CallEdge()
    {
        setSignal(false);
        super.setEndArrowHead(ArrowHead.V);
    }

    /**
     Gets the signal property.
     @return true if this is a signal edge
     */
    public boolean isSignal() { return signal; }

    /**
     Sets the signal property.
     @param newValue true if this is a signal edge
     */
    public void setSignal(boolean newValue)
    {
        signal = newValue;
        if (signal)
            setEndArrowHead(ArrowHead.HALF_V);
        else
            setEndArrowHead(ArrowHead.V);
    }


    public ArrayList getPoints()
    {
        ArrayList a = new ArrayList();
        Node n = getEnd();
        Rectangle2D start = getStart().getBounds();
        Rectangle2D end = n.getBounds();
        if (n instanceof CallNode &&
                ((CallNode)n).getImplicitParameter() ==
                        ((CallNode)getStart()).getImplicitParameter())
        {
            Point2D p = new Point2D(start.getMaxX(), end.getY() - CallNode.CALL_YGAP / 2);
            Point2D q = new Point2D(end.getMaxX(), end.getY());
            Point2D s = new Point2D(q.getX() + end.getWidth(), q.getY());
            Point2D r = new Point2D(s.getX(), p.getY());
            a.add(p);
            a.add(r);
            a.add(s);
            a.add(q);
        }
        else if (n instanceof PointNode) // show nicely in tool bar
        {
            a.add(new Point2D(start.getMaxX(), start.getY()));
            a.add(new Point2D(end.getX(), start.getY()));
        }
        else
        {
            Direction d = new Direction(start.getX() - end.getX(), 0);
            Point2D endPoint = getEnd().getConnectionPoint(d);

            if (start.getCenterX() < endPoint.getX())
                a.add(new Point2D(start.getMaxX(), endPoint.getY()));
            else
                a.add(new Point2D(start.getX(), endPoint.getY()));
            a.add(endPoint);
        }
        return a;
    }
    public Rectangle2D getBounds(Canvas canvas)
    {
        return super.getBounds(canvas);
    }

    private boolean signal;
}