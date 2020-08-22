package com.example.mobileviolet;

/**
 * Created by phoenixnguyen on 4/22/17.
 */


import android.graphics.Canvas;

import java.io.Serializable;
import java.util.List;

/**
 A node in a graph.
 */
public interface Node extends Serializable
{
    /**
     Draw the node.
     */
    void draw(Canvas canvas);
    /**
     Translates the node by a given amount
     @param dx the amount to translate in the x-direction
     @param dy the amount to translate in the y-direction
     */
    void translate(float dx, float dy);

    /**
     Tests whether the node contains a point.
     @param aPoint the point to test
     @return true if this node contains aPoint
     */
    boolean contains(Point2D aPoint);


    /**
     Get the best connection point to connect this node
     with another node. This should be a point on the boundary
     of the shape of this node.
     @param d the direction from the center
     of the bounding rectangle towards the boundary
     @return the recommended connection point
     */
    Point2D getConnectionPoint(Direction d);

    /**
     Get the bounding rectangle of the shape of this node
     @return the bounding rectangle
     */
    Rectangle2D getBounds();

    /**
     Adds an edge that originates at this node.
     @param p1 the point that the user selected as
     the starting point. This may be used as a hint if
     edges are ordered.
     @param e the edge to add
     @return true if the edge was added
     */
    boolean addEdge(Edge e, Point2D p1, Point2D p2);

    /**
     Adds a node as a child node to this node.
     @param n the child node
     @param p the point at which the node is being added
     @return true if this node accepts the given node as a child
     */

    boolean addNode(Node n, Point2D p);


    /**
     Notifies this node that an edge is being removed.
     @param g the ambient graph
     @param e the edge to be removed
     */
    void removeEdge(Graph g, Edge e);

    /**
     Notifies this node that a node is being removed.
     @param g the ambient graph
     @param n the node to be removed
     */
    void removeNode(Graph g, Node n);

    /**
     Lays out the node and its children.
     @param g the ambient graph
     //@param g2 the graphics context
     @param grid the grid to snap to
     */
    void layout(Graph g, Canvas canvas, Grid2 grid);

    /**
     Gets the parent of this node.
     @return the parent node, or null if the node
     has no parent
     */
    Node getParent();

    /**
     Sets the parent of this node.
     @param node the parent node, or null if the node
     has no parent
     */
    void setParent(Node node);

    /**
     Gets the children of this node.
     @return an unmodifiable list of the children
     */
    List getChildren();

    /**
     Adds a child node.
     @param index the position at which to add the child
     @param node the child node to add
     */
    void addChild(int index, Node node);

    /**
     Removes a child node.
    // @param node the child to remove.
     */
    void removeChild(Node node);

    Object clone();

    void setName(MultiLineString text);

}