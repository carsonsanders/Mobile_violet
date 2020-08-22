package com.example.mobileviolet;

/**
 * Created by ginge_000 on 4/24/2017.
 */


import android.graphics.Canvas;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A graph consisting of selectable nodes and edges.
 */
public  class Graph implements Serializable {
    private static final String TAG = "MyActivity";
    private static final long serialVersionUID = 33421;

    /**
     * Constructs a graph with no nodes or edges.
     */
    public Graph() {
        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
        nodesToBeRemoved = new ArrayList<Node>();
        edgesToBeRemoved = new ArrayList<Edge>();
        needsLayout = true;

    }

    /**
     * Adds an edge to the graph that joins the nodes containing
     * the given points. If the points aren't both inside nodes,
     * then no edge is added.
     *
     * @param e  the edge to add
     * @param p1 a point in the starting node
     * @param p2 a point in the ending node
     */

    public boolean connect(Edge e, Point2D p1, Point2D p2) {
        Node n1 = findNode(p1);
        Node n2 = findNode(p2);
        if (n1 != null) {
            e.connect(n1, n2);
            if (n1.addEdge(e, p1, p2) && e.getEnd() != null) {
                edges.add(e);
                if (!nodes.contains(e.getEnd()))
                    nodes.add(e.getEnd());
                needsLayout = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a node to the graph so that the top left corner of
     * the bounding rectangle is at the given point.
     *
     * @param n the node to add
     * @param p the desired location
     */

    public boolean add(Node n, Point2D p) {
        Rectangle2D bounds = n.getBounds();
        n.translate((float) p.getX() - (float) bounds.getX(),
                (float) p.getY() - (float) bounds.getY());

        boolean accepted = false;
        boolean insideANode = false;
        for (int i = nodes.size() - 1; i >= 0 && !accepted; i--) {
            Node parent = (Node) nodes.get(i);
            if (parent.contains(p)) {
                insideANode = true;
                accepted = true;
                if (parent.addNode(n, p)) accepted = true;
            }
        }
        if (insideANode && !accepted)
            return false;
        nodes.add(n);
        Log.v(TAG, "index=" + nodes.size());
        needsLayout = true;
        return true;
    }


    /**
     * Finds a node containing the given point.
     *
     * @param p a point
     * @return a node containing p or null if no nodes contain p
     */
    public Node findNode(Point2D p) {
        for (int i = nodes.size() - 1; i >= 0; i--) {
            Node n = (Node) nodes.get(i);
            if (n.contains(p)) return n;
        }
        return null;
    }

    /**
     * Finds an edge containing the given point.
     *
     * @param p a point
     * @return an edge containing p or null if no edges contain p
     */
    public Edge findEdge(Point2D p) {
        for (int i = edges.size() - 1; i >= 0; i--) {
            Edge e = (Edge) edges.get(i);
            if (e.contains(p)) return e;
        }
        return null;
    }

    /**
     * Draws the graph
     *
     * @param canvas the graphics context
     */
    public void draw(Canvas canvas, Grid2 g) {
        //layout();
        layout(canvas, g);

        for (int i = 0; i < nodes.size(); i++) {
            Node n = (Node) nodes.get(i);
            n.draw(canvas);
        }

        for (int i = 0; i < edges.size(); i++) {
            Edge e = (Edge) edges.get(i);
            e.draw(canvas);
        }
    }

    /**
     * Removes a node and all edges that start or end with that node
     *
     * @param n the node to remove
     */

    public void removeNode(Node n) {
        if (nodesToBeRemoved.contains(n)) return;
        nodesToBeRemoved.add(n);
        // notify nodes of removals
        for (int i = 0; i < nodes.size(); i++) {
            Node n2 = (Node) nodes.get(i);
            n2.removeNode(this, n);
        }
        for (int i = 0; i < edges.size(); i++) {
            Edge e = (Edge) edges.get(i);
            if (e.getStart() == n || e.getEnd() == n)
                removeEdge(e);
        }

        needsLayout = true;
    }

    /**
     * Removes an edge from the graph.
     *
     * @param e the edge to remove
     */

    public void removeEdge(Edge e) {
        if (edgesToBeRemoved.contains(e)) return;
        edgesToBeRemoved.add(e);
        for (int i = nodes.size() - 1; i >= 0; i--) {
            Node n = (Node) nodes.get(i);
            n.removeEdge(this, e);
        }
        needsLayout = true;
    }


    /**
     * Causes the layout of the graph to be recomputed.
     */
    public void layout() {
        needsLayout = true;
    }

    /**
     * Computes the layout of the graph.
     * If you override this method, you must first call
     * <code>super.layout</code>.
     *
     * @param canvas the graphics context
     * @param g      the grid to snap to
     */

    protected void layout(Canvas canvas, Grid2 g) {
        if (!needsLayout) return;
        nodes.removeAll(nodesToBeRemoved);
        edges.removeAll(edgesToBeRemoved);
        nodesToBeRemoved.clear();
        edgesToBeRemoved.clear();

        for (int i = 0; i < nodes.size(); i++) {
            Node n = (Node) nodes.get(i);
            n.layout(this, canvas, g);
        }
        needsLayout = false;
    }


    /**
     Gets the smallest rectangle enclosing the graph
     @param canvas the canvas context
     @return the bounding rectangle
     */
    /**
     * public Rectangle2D getBounds(Canvas canvas)
     * {
     * Rectangle2D r = minBounds;
     * for (int i = 0; i < nodes.size(); i++)
     * {
     * node n = (node)nodes.get(i);
     * Rectangle2D b = n.getBounds();
     * if (r == null) r = b;
     * else r.add(b);
     * }
     * for (int i = 0; i < edges.size(); i++)
     * {
     * Edge e = (Edge)edges.get(i);
     * r.add(e.getBounds(canvas));
     * }
     * return r == null ? new Rectangle2D.Double() : new Rectangle2D.Double(r.getX(), r.getY(),
     * r.getWidth() + node.SHADOW_GAP, r.getHeight() + node.SHADOW_GAP);
     * }
     */

    public Rectangle2D getMinBounds() {
        return minBounds;
    }

    public void setMinBounds(Rectangle2D newValue) {
        minBounds = newValue;
    }

    /**
     Gets the node types of a particular graph type.
     @return an array of node prototypes
     */
    //public abstract node[] getNodePrototypes();

    /**
     Gets the edge types of a particular graph type.
     @return an array of edge prototypes
     */
    //public abstract Edge[] getEdgePrototypes();

    /**
     Adds a persistence delegate to a given encoder that
     encodes the child nodes of this node.
     @param encoder the encoder to which to add the delegate
     */
    /**
     public static void setPersistenceDelegate(Encoder encoder)
     {
     encoder.setPersistenceDelegate(Graph.class, new
     DefaultPersistenceDelegate()
     {
     protected void initialize(Class type,
     Object oldInstance, Object newInstance,
     Encoder out)
     {
     super.initialize(type, oldInstance,
     newInstance, out);
     Graph g = (Graph)oldInstance;

     for (int i = 0; i < g.nodes.size(); i++)
     {
     node n = (node)g.nodes.get(i);
     Rectangle2D bounds = n.getBounds();
     Point2D p = new Point2D.Double(bounds.getX(),
     bounds.getY());
     out.writeStatement(
     new Statement(oldInstance,
     "addNode", new Object[]{ n, p }) );
     }
     for (int i = 0; i < g.edges.size(); i++)
     {
     Edge e = (Edge)g.edges.get(i);
     out.writeStatement(
     new Statement(oldInstance,
     "connect",
     new Object[]{ e, e.getStart(), e.getEnd() }) );
     }
     }
     });
     }
     */

    /**
     * Gets the nodes of this graph.
     *
     * @return an unmodifiable collection of the nodes
     */
    public Collection getNodes() {
        return nodes;
    }

    /**
     * Gets the edges of this graph.
     *
     * @return an unmodifiable collection of the edges
     */
    public Collection getEdges() {
        return edges;
    }

    /**
     * Adds a node to this graph. This method should
     * only be called by a decoder when reading a data file.
     *
     * @param n the node to add
     * @param p the desired location
     */

    public void addNode(Node n, Point2D p) {
        Rectangle2D bounds = n.getBounds();
        n.translate((float) p.getX() - (float) bounds.getX(),
                (float) p.getY() - (float) bounds.getY());
        nodes.add(n);
    }


    /**
     * Adds an edge to this graph. This method should
     * only be called by a decoder when reading a data file.
     *
     * @param e     the edge to add
     * @param start the start node of the edge
     * @param end   the end node of the edge
     */
    public void connect(Edge e, Node start, Node end) {
        e.connect(start, end);
        edges.add(e);
    }

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private ArrayList<Node> nodesToBeRemoved;
    private ArrayList<Edge> edgesToBeRemoved;
    private boolean needsLayout;
    private transient Rectangle2D minBounds;
}
