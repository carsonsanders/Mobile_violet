package com.example.mobileviolet;

import android.graphics.Canvas;
import android.telecom.Call;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;



/**
 * Created by phoenixnguyen on 4/29/17.
 */

public class SequenceDiagramGraph extends Graph
{
    public SequenceDiagramGraph()
    {
        super();
    }

    public boolean add(Node n, Point2D p)
    {
        if (n instanceof CallNode) // must be inside an object
        {
            Collection nodes = getNodes();
            boolean inside = false;
            Iterator iter = nodes.iterator();
            while (!inside && iter.hasNext())
            {
                Node n2 = (Node)iter.next();
                if (n2 instanceof ImplicitParameterNode
                        && n2.contains(p))
                {
                    inside = true;
                    ((CallNode)n).setImplicitParameter(
                            (ImplicitParameterNode)(n2));
                }
            }
            if (!inside) return false;
        }

        if (!super.add(n, p)) return false;

        return true;
    }
/**
    public void removeEdge(Edge e)
    {
        super.removeEdge(e);
        if (e instanceof CallEdge && e.getEnd().getChildren().size() == 0)
            removeNode(e.getEnd());
    }
 */

    public void layout(Canvas canvas, Grid2 grid)
    {
        super.layout(canvas, grid);

        ArrayList topLevelCalls = new ArrayList();
        ArrayList objects = new ArrayList();
        Collection nodes = getNodes();
        Iterator iter = nodes.iterator();
        while (iter.hasNext())
        {
            Node n = (Node)iter.next();

            if (n instanceof CallNode && n.getParent() == null)
                topLevelCalls.add(n);
            else if (n instanceof ImplicitParameterNode)
                objects.add(n);
        }


        Collection edges = getEdges();
        iter = edges.iterator();
        while (iter.hasNext())
        {
            Edge e = (Edge)iter.next();
            if (e instanceof CallEdge)
            {
                Node end = e.getEnd();
                if (end instanceof CallNode)
                    ((CallNode)end).setSignaled(((CallEdge)e).isSignal());
            }
        }


        double left = 0;

        // find the max of the heights of the objects

        double top = 0;
        for (int i = 0; i < objects.size(); i++)
        {
            if (!(objects.get(i) instanceof NoteNode)) {
                ImplicitParameterNode n = (ImplicitParameterNode) objects.get(i);
                n.translate(0, (float) -n.getBounds().getY());
                top = Math.max(top, n.getTopRectangle().getHeight());
            }
        }

      /*

      // sort topLevelCalls by y position
      Collections.sort(topLevelCalls, new
         Comparator()
         {
            public int compare(Object o1, Object o2)
            {
               CallNode c1 = (CallNode)o1;
               CallNode c2 = (CallNode)o2;
               double diff = c1.getBounds().getY()
                  - c2.getBounds().getY();
               if (diff < 0) return -1;
               if (diff > 0) return 1;
               return 0;
            }
         });

      for (int i = 0; i < topLevelCalls.size(); i++)
      {
         CallNode call = (CallNode)topLevelCalls.get(i);
         top += CallNode.CALL_YGAP;

         call.translate(0, top - call.getBounds().getY());
         call.layout(this, g2, grid);
         top += call.getBounds().getHeight();
      }
      */


        for (int i = 0; i < topLevelCalls.size(); i++)
        {
            CallNode call = (CallNode) topLevelCalls.get(i);
            call.layout(this, canvas, grid);
        }

        iter = nodes.iterator();
        while (iter.hasNext())
        {
            Node n = (Node)iter.next();
            if (n instanceof CallNode)
                top = Math.max(top, n.getBounds().getY()
                        + n.getBounds().getHeight());
        }

        top += CallNode.CALL_YGAP;

        for (int i = 0; i < objects.size(); i++)
        {
            if (!(objects.get(i) instanceof NoteNode)) {
                ImplicitParameterNode n = (ImplicitParameterNode) objects.get(i);
                Rectangle2D b = n.getBounds();
                n.setBounds(new Rectangle2D(
                        b.getX(), b.getY(),
                        b.getWidth(), top - b.getY()));
            }
        }
    }


    public void draw(Canvas canvas, Grid2 g)
    {
        layout(canvas, g);

        Collection nodes = getNodes();
        Iterator iter = nodes.iterator();
        while (iter.hasNext())
        {
            Node n = (Node) iter.next();
            if (!(n instanceof CallNode))
                n.draw(canvas);
        }

        iter = nodes.iterator();
        while (iter.hasNext())
        {
            Node n = (Node) iter.next();
            if (n instanceof CallNode)
                n.draw(canvas);
        }

        Collection edges = getEdges();
        iter = edges.iterator();
        while (iter.hasNext())
        {
            Edge e = (Edge) iter.next();
            e.draw(canvas);
        }

    }

    public Node[] getNodePrototypes()
    {
        return NODE_PROTOTYPES;
    }

    public Edge[] getEdgePrototypes()
    {
        return EDGE_PROTOTYPES;
    }

    private static final Node[] NODE_PROTOTYPES = new Node[3];

    private static final Edge[] EDGE_PROTOTYPES = new Edge[3];

    static
    {
        NODE_PROTOTYPES[0] = new ImplicitParameterNode();
        NODE_PROTOTYPES[1] = new CallNode();
        NODE_PROTOTYPES[2] = new NoteNode();

        //NODE_PROTOTYPES[2] = new NoteNode();
        EDGE_PROTOTYPES[0] = new CallEdge();
        //EDGE_PROTOTYPES[1] = new ReturnEdge();
        //EDGE_PROTOTYPES[2] = new NoteEdge();

    }
}