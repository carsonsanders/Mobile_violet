package com.example.mobileviolet;

/**
 * Created by hayleycarpenter on 2017-04-25.
 */
        import android.graphics.Canvas;


/**
 A class that supplies convenience implementations for
 a number of methods in the node interface
 */
abstract class AbstractEdge implements Edge {
//    public Object clone()
//    {
//        try
//        {
//            return super.clone();
//        }
//        catch (CloneNotSupportedException exception)
//        {
//            return null;
//        }
//    }

    public void connect(Node s, Node e) {
        start = s;
        end = e;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }


    public Rectangle2D getBounds(Canvas canvas) {
        Line2D conn = getConnectionPoints();
        Rectangle2D r = new Rectangle2D();
        r.setFrame(conn.getX1(), conn.getY1(),
                conn.getX2(), conn.getY2());
        return r;
    }


    public Line2D getConnectionPoints()
    {
        Rectangle2D startBounds = start.getBounds();
        Rectangle2D endBounds = end.getBounds();
        Point2D startCenter = new Point2D(
                startBounds.getCenterX(), startBounds.getCenterY());
        Point2D endCenter = new Point2D(
                endBounds.getCenterX(), endBounds.getCenterY());
        Direction toEnd = new Direction(startCenter, endCenter);
        return new Line2D(
                start.getConnectionPoint(toEnd),
                end.getConnectionPoint(toEnd.turn(180)));
    }


    private Node start;
    private Node end;
}