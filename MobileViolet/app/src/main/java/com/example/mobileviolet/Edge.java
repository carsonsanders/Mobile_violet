package com.example.mobileviolet;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.io.Serializable;

/**
 * Created by hayleycarpenter on 2017-04-25.
 */


/**
 An edge in a graph.
 */
public interface Edge extends Serializable
{
    /**
     Draw the edge.
     @param canvas the graphics context
     */
    void draw(Canvas canvas);

    /**
     Tests whether the edge contains a point.
     @param aPoint the point to test
     @return true if this edge contains aPoint
     */
    boolean contains(Point2D aPoint);

    /**
     Connect this edge to two nodes.
     @param aStart the starting node
     @param anEnd the ending node
     */
    void connect(Node aStart, Node anEnd);

    /**
     Gets the starting node.
     @return the starting node
     */
    Node getStart();

    /**
     Gets the ending node.
     @return the ending node
     */
    Node getEnd();

    /**
     Gets the points at which this edge is connected to
     its nodes.
     @return a line joining the two connection points
     */
    Line2D getConnectionPoints();

    /**
     Gets the smallest rectangle that bounds this edge.
     The bounding rectangle contains all labels.
     @return the bounding rectangle
     */
    Rectangle2D getBounds(Canvas canvas);

    //Object clone();
}

