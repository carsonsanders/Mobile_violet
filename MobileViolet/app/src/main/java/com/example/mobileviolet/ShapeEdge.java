package com.example.mobileviolet;

/**
 * Created by ginge_000 on 4/27/2017.
 */

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;

/**
 A class that assumes that an edge can yield its shape
 and then takes advantage of the fact that containment testing can
 be done by stroking the shape with a fat stroke.
 */

public abstract class ShapeEdge extends AbstractEdge
{
    private static final long serialVersionUID = 445553;
    /**
     Returns the path that should be stroked to
     draw this edge. The path does not include
     arrow tips or labels.
     @return a path along the edge
     */

    public abstract Path getShape();

    public Rectangle2D getBounds(Canvas canvas)
    {
        // Path is a bitch to get a bounding rectangle out of. thats what this code does.
        RectF rect = new RectF();
        getShape().computeBounds(rect, true);
        Rectangle2D bounds = new Rectangle2D(rect.left, rect.top,rect.right - rect.left, rect.bottom - rect.top);
        return bounds;
    }

    public boolean contains(Point2D aPoint)
    {
        final double MAX_DIST = 3;

        // the end points may contain small nodes, so don't match them
        Line2D conn = getConnectionPoints();
        // if (aPoint.distance(conn.getP1()) <= MAX_DIST
        //|| aPoint.distance(conn.getP2()) <= MAX_DIST)
        //return false;

        /** Not sure how this works or why, going to try other method
         Shape p = getShape();
         BasicStroke fatStroke = new BasicStroke(
         (float)(2 * MAX_DIST));
         Shape fatPath = fatStroke.createStrokedShape(p);
         return fatPath.contains(aPoint);
         */
        RectF rect = new RectF();
        getShape().computeBounds(rect, true);
        Rectangle2D bounds = new Rectangle2D(rect.left - 15, rect.top - 15,rect.right - rect.left + 30, rect.bottom - rect.top + 30);

        return bounds.contains(aPoint.getX(), aPoint.getY());
        //return false;
    }
}