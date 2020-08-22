package com.example.mobileviolet;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

/**
 * Created by phoenixnguyen on 4/23/17.
 */

public class NoteNode extends ImplicitParameterNode {

    private static int FOLD_X = 50;
    private static int FOLD_Y = 50;
    private static int DEFAULT_WIDTH = 320;
    private static int DEFAULT_HEIGHT = 480;
    private static int DEFAULT_TOP_HEIGHT = 240;
    private Rectangle2D bounds;
    private int color;


    public NoteNode() {
        setColor(0xFFFFF06B);
    }

    @Override
    public void layout(Graph graph, Canvas canvas, Grid2 grid)
    {
        Rectangle2D b = getName().getBounds(canvas); // getMultiLineBounds(name, g2);
        Rectangle2D bounds = getBounds();
        b = new Rectangle2D(bounds.getX(),
                bounds.getY(),
                Math.max(b.getWidth(), DEFAULT_WIDTH),
                DEFAULT_TOP_HEIGHT);
        grid.snap(b);
        setBounds(b);
    }

    @Override
    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(getColor());
        paint.setStyle(Paint.Style.FILL);
        Path path = getShape();
        canvas.drawPath(path, paint);

        Rectangle2D bounds = this.getBounds();
        //getBounds().draw(canvas, paint);
        Path fold = new Path();
        fold.moveTo((float)(bounds.getMaxX() - FOLD_X), (float)bounds.getY());
        fold.lineTo((float)bounds.getMaxX() - FOLD_X, (float)bounds.getY() + FOLD_X);
        fold.lineTo((float)bounds.getMaxX(), (float)(bounds.getY() + FOLD_Y));
        paint.setColor(Color.WHITE);
        canvas.drawPath(fold, paint);

        paint.reset();
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);
        canvas.drawPath(fold, paint);


        paint.reset();
        if (getName() != null)
        {
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setStrokeWidth(1);
            getName().draw(canvas, getBounds(), paint);


        }


    }

    public Path getShape()
    {
        Rectangle2D bounds = this.getBounds();
        Path path = new Path();
        path.moveTo((float)bounds.getX(), (float)bounds.getY());
        path.lineTo((float)(bounds.getMaxX() - FOLD_X), (float)bounds.getY());
        path.lineTo((float)bounds.getMaxX(), (float)(bounds.getY() + FOLD_Y));
        path.lineTo((float)bounds.getMaxX(), (float)bounds.getMaxY());
        path.lineTo((float)bounds.getX(), (float)bounds.getMaxY());
        path.close();
        return path;
    }

    @Override
    public boolean contains(Point2D point)
    {
        //return bounds.contains(point.getX(), point.getY());
        Rectangle2D bounds = getBounds();
        return bounds.contains(point.getX(), point.getY());
    }
    public int getColor()
    {
        return color;
    }

    public void setColor(int newColor)
    {
        color = newColor;
    }

    public boolean addEdge(Edge e, Point2D p1, Point2D p2)
    {
        PointNode end = new PointNode(p2.getX(), p2.getY());
        end.translate(p2.getX(), p2.getY());
        e.connect(this, end);
        return e.getEnd() != null;
    }

    public void removeEdge(Graph g, Edge e)
    {
        if (e.getStart() == this) g.removeNode(e.getEnd());
    }
}