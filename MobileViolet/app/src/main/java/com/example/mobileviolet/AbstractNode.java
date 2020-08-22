package com.example.mobileviolet;

/**
 * Created by kitt3 on 2017-05-01.
 */
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.shapes.Shape;
import java.util.List;
import java.util.ArrayList;


/**
 A class that supplies convenience implementations for
 a number of methods in the Node interface
 */

public abstract class AbstractNode implements Node
{
    /**
     Constructs a node with no parents or children.
     */
    public AbstractNode()
    {
        children = new ArrayList();
        parent = null;
    }

//    public Object clone()
//    {
//        try
//        {
//            AbstractNode cloned = (AbstractNode)super.clone();
//            cloned.children = new ArrayList(children.size());
//            for (int i = 0; i < children.size(); i++)
//            {
//                Node n = (Node)children.get(i);
//                cloned.children.set(i, n.clone());
//                n.setParent(cloned);
//            }
//            return cloned;
//        }
//        catch (CloneNotSupportedException exception)
//        {
//            return null;
//        }
//    }

    public void translate(double dx, double dy)
    {
        for (int i = 0; i < children.size(); i++)
        {
            Node n = (Node)children.get(i);
            n.translate((float)dx, (float)dy);
        }
    }

    public boolean addEdge(Edge e, Point2D p1, Point2D p2)
    {
        return e.getEnd() != null;
    }

    public void removeEdge(Graph g, Edge e)
    {
    }

    public void removeNode(Graph g, Node e)
    {
        if (e == parent) parent = null;
        if (e.getParent() == this) children.remove(e);
    }

    public void layout(Graph g, Canvas canvas, Grid grid)
    {
    }

    public boolean addNode(Node n, Point2D p)
    {
        return false;
    }

    public void layout(Graph g, Canvas canvas, Grid2 grid) {

    }

    public Node getParent() { return parent; }

    @Override
    public Object clone() {
        return null;
    }


    public void setName(MultiLineString text) {

    }

    public void setParent(AbstractNode abstractNode) {

    }

    public void setParent(Node node) { parent = node; }

    public List getChildren() { return children; }

    /**
    public void addChild(int index, Node node)
    {
        Node oldParent = node.getParent();
        if (oldParent != null)
            oldParent.removeChild(node);
        children.add(index, node);
        node.setParent(this);
    }
     */

    //public void addChild(Node node)
    {
        //addChild(children.size(), node);
    }

    public void removeChild(Node node)
    {
        if (node.getParent() != this) return;
        children.remove(node);
        node.setParent(null);
    }

    public void draw(Canvas canvas)
    {
        Shape shape = getShape();
        if (shape == null) return;
      /*
      Area shadow = new Area(shape);
      shadow.transform(AffineTransform.getTranslateInstance(SHADOW_GAP, SHADOW_GAP));
      shadow.subtract(new Area(shape));
      */

      /**
        Color oldColor = canvas.getColor();
        canvas.translate(SHADOW_GAP, SHADOW_GAP);
        canvas.setColor(Color.parseColor(SHADOW_COLOR));
        canvas.fill(shape);
        canvas.translate(-SHADOW_GAP, -SHADOW_GAP);
        canvas.setColor(canvas.getBackground());
        canvas.fill(shape);
        canvas.drawPaint(oldColor);
       */

//        Color oldColor = canvas.getColor();
//        canvas.translate(SHADOW_GAP, SHADOW_GAP);
//        canvas.setColor(Color.parseColor(SHADOW_COLOR));
//        canvas.fill(shape);
//        canvas.translate(-SHADOW_GAP, -SHADOW_GAP);
//        canvas.setColor(canvas.getBackground());
//        canvas.fill(shape);
//        canvas.drawPaint(oldColor);
    }


    public void translate(float dx, float dy) {

    }


    public boolean contains(Point2D aPoint) {
        return false;
    }


    public Point2D getConnectionPoint(Direction d) {
        return null;
    }


    public Rectangle2D getBounds() {
        return null;
    }

    private static final int SHADOW_COLOR = Color.GRAY;
    public static final int SHADOW_GAP = 4;

    /**
     @return the shape to be used for computing the drop shadow
     */
    public Shape getShape() { return null; }

    /**
     Adds a persistence delegate to a given encoder that
     encodes the child nodes of this node.
     @param encoder the encoder to which to add the delegate
     */
//    public static void setPersistenceDelegate(Encoder encoder)
//    {
//        encoder.setPersistenceDelegate(AbstractNode.class, new
//                DefaultPersistenceDelegate()
//                {
//                    protected void initialize(Class type,
//                                              Object oldInstance, Object newInstance,
//                                              Encoder out)
//                    {
//                        super.initialize(type, oldInstance,
//                                newInstance, out);
//                        Node n = (Node)oldInstance;
//                        List children = n.getChildren();
//                        for (int i = 0; i < children.size(); i++)
//                        {
//                            Node c = (Node)children.get(i);
//                            out.writeStatement(
//                                    new Statement(oldInstance,
//                                            "addChild", new Object[]{ c }) );
//                        }
//                    }
//                });
//    }
    private ArrayList children;
    private Node parent;
}