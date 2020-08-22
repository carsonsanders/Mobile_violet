package com.example.mobileviolet;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.constraint.solver.widgets.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;

/**
 * Created by phoenixnguyen on 4/22/17.
 */

public class ImplicitParameterNode implements Node {
    public Rectangle2D rect;

    private static int DEFAULT_WIDTH = 320;
    private static int DEFAULT_TOP_HEIGHT = 240;
    private static int DEFAULT_HEIGHT = 480;
    private MultiLineString name;
    public Rectangle2D bounds;
    private ArrayList children;
    private Node parent;

    private float x;
    private float y;
    private int topHeight;

    public ImplicitParameterNode(float x, float y)
    {
        setBounds(new Rectangle2D(0, 0,
                DEFAULT_WIDTH, DEFAULT_HEIGHT));
        topHeight = DEFAULT_TOP_HEIGHT;
        name = new MultiLineString();
        children = new ArrayList();
        parent = null;

    }

    public ImplicitParameterNode()
    {
        bounds = getBounds();
        setBounds(new Rectangle2D(0, 0,
                DEFAULT_WIDTH, DEFAULT_HEIGHT));
        topHeight = DEFAULT_TOP_HEIGHT;
        name = new MultiLineString();
    }

    @Override
    public boolean contains(Point2D point)
    {
        Rectangle2D bounds = getBounds();
        return bounds.getX() <= point.getX() &&
                point.getX() <= bounds.getX() + bounds.getWidth();
    }

    @Override
    public void draw(Canvas canvas)
    {

        Rectangle2D top = getTopRectangle();
        float midX = (float)getBounds().getCenterX();
        Path path = new Path();
        path.moveTo(midX, (float)(top.getMaxY()));
        path.lineTo(midX, (float)(getBounds().getMaxY()));
        Paint paint = new Paint();
        paint.setColor(Color.argb(255,255,255,255));
        if (top != null) {
            top.draw(canvas, paint);
            paint.reset();
            paint.setStrokeWidth(3);
            paint.setStyle(Paint.Style.STROKE);
            top.draw(canvas, paint);
            //getBounds().draw(canvas, paint); //for checking out bounds
            if (getName() != null)
            {
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                paint.setStrokeWidth(1);
                //getName().getBounds(canvas).draw(canvas, paint);
                getName().draw(canvas, getTopRectangle(), paint);


            }
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3);
            paint.setPathEffect(new DashPathEffect(new float[]{15, 15}, 0));
            canvas.drawPath(path, paint);
        }
    }

    public Rectangle2D getTopRectangle()
    {
        return new Rectangle2D(getBounds().getX(), getBounds().getY(), getBounds().getWidth(), topHeight);
    }

    public boolean addEdge(Edge e, Point2D p1, Point2D p2)
    {
        return false;
    }

    @Override
    public Point2D getConnectionPoint(Direction d)
    {
        if (d.getX() > 0)
            return new Point2D(getBounds().getMaxX(),
                    getBounds().getY() + topHeight / 2);
        else
            return new Point2D(getBounds().getX(),
                    getBounds().getY() + topHeight / 2);
    }

    public void layout(Graph g, Canvas canvas, Grid2 grid)
    {

        Rectangle2D b = getName().getBounds(canvas);
        b.add(new Rectangle2D(0, 0, DEFAULT_WIDTH,
                DEFAULT_TOP_HEIGHT));

        Rectangle2D top = new Rectangle2D(
                getBounds().getX(), getBounds().getY(),
                b.getWidth(), b.getHeight());
        grid.snap(top);
        setBounds(new Rectangle2D(
                top.getX(), top.getY(),
                top.getWidth(), getBounds().getHeight()));
        topHeight = (int)top.getHeight();
    }

    public void setName(MultiLineString n)
    {
        name = n;
    }

    /**
     Gets the name property value.
     @return the name of this object
     */
    public MultiLineString getName()
    {
        return name;
    }

    public boolean addNode(Node n, Point2D p)
    {
        return n instanceof CallNode || n instanceof PointNode;
    }


    @Override
    public Rectangle2D getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle2D newRect)
    {
        bounds = newRect;
    }

    @Override
    public void translate(float dx, float dy) //from RectangularNode
    {
        bounds.setFrame(bounds.getX() + dx,
                bounds.getY() + dy,
                bounds.getWidth(),
                bounds.getHeight());
    }

    public List getChildren() { return children; }


    public void addChild(int index, Node node)
    {
        Node oldParent = node.getParent();
        if (oldParent != null)
            oldParent.removeChild(node);
        children.add(index, node);
        node.setParent(this);
    }

    public void addChild(Node node)
    {
        addChild(children.size(), node);
    }

    public void removeChild(Node node)
    {
        if (node.getParent() != this) return;
        children.remove(node);
        node.setParent(null);
    }

    public void setParent(Node node) { parent = node; }

    public Node getParent()
    {
        return parent;
    }

    public void removeEdge(Graph g, Edge e)
    {
    }

    public void removeNode(Graph g, Node e)
    {
        if (e == parent) parent = null;
        if (e.getParent() == this) children.remove(e);
    }

    @Override
    public Object clone()
    {
        return null;
    }





}