package com.example.mobileviolet;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.telecom.Call;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by phoenixnguyen on 4/24/17.
 */


public class CallNode implements Node{
    private ImplicitParameterNode implicitNode;
    private boolean signaled;
    private boolean openBottom;
    private Rectangle2D bounds;
    private ArrayList children;
    private Node parent;

    private static int DEFAULT_WIDTH = 64;
    private static int DEFAULT_HEIGHT = 120;
    public static int CALL_YGAP = 30;

    public CallNode()
    {
        setBounds(new Rectangle2D(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT));
        children = new ArrayList();
        parent = null;

    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        //super.draw(canvas);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        getBounds().draw(canvas, paint);

        paint.reset();
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        if (openBottom)
        {
            Rectangle2D b = getBounds();
            float x1 = (float)b.getX();
            float x2 = (float)(x1 + b.getWidth());
            float y1 = (float)(b.getY());
            float y3 = (float)(y1 + b.getHeight());
            float y2 = (float)(y3 - CALL_YGAP);
            canvas.drawLine(x1, y1, x2, y1, paint);
            canvas.drawLine(x1, y1, x1, y2, paint);
            canvas.drawLine(x2, y1, x2, y2, paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3);
            paint.setPathEffect(new DashPathEffect(new float[]{15, 15}, 0));

            Path path1 = new Path();
            path1.moveTo(x1, y2);
            path1.lineTo(x1, y3);
            Path path2 = new Path();
            path2.moveTo(x2, y2);
            path2.lineTo(x2, y3);

            canvas.drawPath(path1, paint);
            canvas.drawPath(path2, paint);
        }
        else
            getBounds().draw(canvas, paint);

    }

    public ImplicitParameterNode getImplicitParameter()
    {
        return implicitNode;
    }

    public void setImplicitParameter(ImplicitParameterNode newValue)
    {
        implicitNode = newValue;
    }

    @Override
    public Point2D getConnectionPoint(Direction d) {
        if (d.getX() > 0)
            return new Point2D(getBounds().getMaxX(),
                    getBounds().getY());
        else
            return new Point2D(getBounds().getX(),
                    getBounds().getY());
    }

    public boolean addEdge(Edge e, Point2D p1, Point2D p2)
    {
        Node end = e.getEnd();
        if (end == null) return false;

        if (e instanceof ReturnEdge) // check that there is a matching call
            return end == getParent();

        if (!(e instanceof CallEdge)) return false;

        Node n = null;
        if (end instanceof CallNode)
        {
            // check for cycles
            Node parent = this;
            while (parent != null && end != parent)
                parent = parent.getParent();

            if (end.getParent() == null && end != parent)
            {
                n = end;
            }
            else
            {
                CallNode c = new CallNode();
                c.implicitNode = ((CallNode)end).implicitNode;
                e.connect(this, c);
                n = c;
            }
        }
        else if (end instanceof ImplicitParameterNode)
        {
            if (((ImplicitParameterNode)end).getTopRectangle().contains(p2.getX(), p2.getY()))
            {
                n = end;
                ((CallEdge)e).setMiddleLabel("\u00ABcreate\u00BB"); // apparently it works!
            }
            else
            {
                CallNode c = new CallNode();
                c.implicitNode = (ImplicitParameterNode)end;
                e.connect(this, c);
                n = c;
            }
        }
        else return false;

        int i = 0;
        List calls = getChildren();
        while (i < calls.size() && ((Node)calls.get(i)).getBounds().getY() <= p1.getY())
            i++;
        addChild(i, n);
        return true;
    }

    public void removeEdge(Graph g, Edge e)
    {
        if (e.getStart() == this)
            removeChild(e.getEnd());
    }

    public void removeNode(Graph g, Node n)
    {
        if (n == getParent() || n == implicitNode)
            g.removeNode(this); //graph doesn't have remove node?
    }

    /*
    private static Edge findEdge(Graph g, Node start, Node end)
    {
        Collection edges = g.getEdges();
        Iterator iter = edges.iterator();
        while (iter.hasNext())
        {
            Edge e = (Edge) iter.next();
            if (e.getStart() == start && e.getEnd() == end) return e;
        }
        return null;
    }
    */


    public void layout(Graph graph, Canvas canvas, Grid2 grid) {

        if (implicitNode == null) return;
        double xmid = implicitNode.getBounds().getCenterX();

        for (CallNode c = (CallNode)getParent();
             c != null; c = (CallNode)c.getParent())
            if (c.implicitNode == implicitNode)
                xmid += getBounds().getWidth() / 2;

        translate((float)(xmid - getBounds().getCenterX()), 0);
        double ytop = getBounds().getY() + CALL_YGAP;


        List calls = getChildren();
        for (int i = 0; i < calls.size(); i++)
        {
            Node n = (Node) calls.get(i);
            if (n instanceof ImplicitParameterNode) // <<create>>
            {
                n.translate(0, (float)(ytop - ((ImplicitParameterNode) n).getTopRectangle().getCenterY()));
                ytop += ((ImplicitParameterNode)n).getTopRectangle().getHeight() / 2 + CALL_YGAP;
            }

            else if (n instanceof CallNode)
            {
                Edge callEdge = findEdge(graph, this, n);
                // compute height of call edge
                if (callEdge != null)
                {
                    Rectangle2D edgeBounds = callEdge.getBounds(canvas);
                    ytop += edgeBounds.getHeight() - CALL_YGAP;
                }

                n.translate(0, (float)(ytop - n.getBounds().getY()));
                n.layout(graph, canvas, grid);
                if (((CallNode) n).signaled)
                    ytop += CALL_YGAP;
                else
                    ytop += n.getBounds().getHeight() + CALL_YGAP;
            }

        }

        if (openBottom) ytop += 2 * CALL_YGAP;
        Rectangle2D b = getBounds();

        double minHeight = DEFAULT_HEIGHT;

        Edge returnEdge = findEdge(graph, this, getParent());
        if (returnEdge != null)
        {
            Rectangle2D edgeBounds = returnEdge.getBounds(canvas);
            minHeight = Math.max(minHeight, edgeBounds.getHeight());
        }

        setBounds(new Rectangle2D(b.getX(), b.getY(), b.getWidth(),
                Math.max(minHeight, ytop - b.getY())));

    }

    public boolean addNode(Node n, Point2D p)
    {
        return n instanceof PointNode;

    }

    public void setSignaled(boolean newValue) { signaled = newValue; }

    /**
     Gets the openBottom property.
     @return true if this node is the target of a signal edge
     */
    public boolean isOpenBottom() { return openBottom; }

    /**
     Sets the openBottom property.
     @param newValue true if this node is the target of a signal edge
     */
    public void setOpenBottom(boolean newValue) { openBottom = newValue; }



    @Override
    public void translate(float dx, float dy) {

        bounds.setFrame(bounds.getX() + dx,
                bounds.getY() + dy,
                bounds.getWidth(),
                bounds.getHeight());
        //super.translate(dx, dy);
    }

    @Override
    public boolean contains(Point2D aPoint) {
        final float MAX_DIST = 20;

        Rectangle2D fatBounds = new Rectangle2D();
        fatBounds.setFrame(bounds.getX() - MAX_DIST, bounds.getY() - MAX_DIST, bounds.getWidth() + 2*MAX_DIST, bounds.getHeight() + 2*MAX_DIST);
        return fatBounds.contains(aPoint.getX(), aPoint.getY());
    }


    @Override
    public Rectangle2D getBounds() {
        return bounds;
    }

    public Node getParent() { return parent; }

    @Override
    public Object clone()
    {
        try
        {
            CallNode cloned = (CallNode) super.clone();
            //cloned.children = new ArrayList(children.size());
            //for (int i = 0; i < children.size(); i++)
            //{
            // Node n = (Node)children.get(i);
            //cloned.children.set(i, n.clone());
            //n.setParent(cloned);
            //}
            return cloned;
        }
        catch (CloneNotSupportedException exception)
        {
            return null;
        }
    }

    public void setParent(Node node) { parent = node; }

    public void setBounds(Rectangle2D newBounds)
    {
        bounds = newBounds;
    }

    private static Edge findEdge(Graph g, Node start, Node end)
    {
        Collection edges = g.getEdges();
        Iterator iter = edges.iterator();
        while (iter.hasNext())
        {
            Edge e = (Edge) iter.next();
            if (e.getStart() == start && e.getEnd() == end) return e;
        }
        return null;
    }



    public void setName(MultiLineString text) {}


    public List getChildren() { return children; }

    public void addChild(int index, Node node)
    {
        Node oldParent = node.getParent();
        if (oldParent != null)
            oldParent.removeChild(node);
        children.add(index, node);
        node.setParent(this);
    }

    public void removeChild(Node node)
    {
        if (node.getParent() != this) return;
        children.remove(node);
        node.setParent(null);
    }





}