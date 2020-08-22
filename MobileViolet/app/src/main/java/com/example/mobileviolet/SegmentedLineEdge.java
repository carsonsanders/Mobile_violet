package com.example.mobileviolet;

/**
 * Created by ginge_000 on 4/28/2017.
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.telecom.Call;

import java.util.ArrayList;

/**
 An edge that is composed of multiple line segments
 */
public abstract class SegmentedLineEdge extends ShapeEdge
{
    /**
     Constructs an edge with no adornments.
     */
    public SegmentedLineEdge()
    {
        // lineStyle = LineStyle.SOLID;
        startArrowHead = ArrowHead.NONE;
        endArrowHead = ArrowHead.NONE;
        startLabel = "";
        middleLabel = "";
        endLabel = "";
        text = new MultiLineString();
    }

    /**
     Sets the line style property.
     @param newValue the new value
     */
    //public void setLineStyle(LineStyle newValue) { lineStyle = newValue; }

    /**
     Gets the line style property.
     @return the line style
     */
    //public LineStyle getLineStyle() { return lineStyle; }

    /**
     Sets the start arrow head property
     @param newValue the new value
     */
    public void setStartArrowHead(ArrowHead newValue) { startArrowHead = newValue; }

    /**
     Gets the start arrow head property
     @return the start arrow head style
     */
    public ArrowHead getStartArrowHead() { return startArrowHead; }

    /**
     Sets the end arrow head property
     @param newValue the new value
     */
    public void setEndArrowHead(ArrowHead newValue) { endArrowHead = newValue; }

    /**
     Gets the end arrow head property
     @return the end arrow head style
     */
    public ArrowHead getEndArrowHead() { return endArrowHead; }

    /**
     Sets the start label property
     @param newValue the new value
     */
    public void setStartLabel(String newValue) { startLabel = newValue; }

    /**
     Gets the start label property
     @return the label at the start of the edge
     */
    public String getStartLabel() { return startLabel; }

    /**
     Sets the middle label property
     @param newValue the new value
     */
    public void setMiddleLabel(String newValue) { middleLabel = newValue; }

    /**
     Gets the middle label property
     @return the label at the middle of the edge
     */
    public String getMiddleLabel() { return middleLabel; }

    /**
     Sets the end label property
     @param newValue the new value
     */
    public void setEndLabel(String newValue) { endLabel = newValue; }

    /**
     Gets the end label property
     @return the label at the end of the edge
     */
    public String getEndLabel() { return endLabel; }

    /**
     Draws the edge.
     @param canvas the graphics context
     */
    public void draw(Canvas canvas)
    {
        ArrayList points = getPoints();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        if (this instanceof ReturnEdge){

            paint.setPathEffect(new DashPathEffect(new float[]{15, 15}, 0));
        }
        canvas.drawRect(new Rect(100,100,100,100), paint);

        // g2.setStroke(lineStyle.getStroke()); Might need a replacement for path, for now just using basic paint
        canvas.drawPath(getSegmentPath(), paint);
        startArrowHead.draw(canvas, (Point2D)points.get(1),
                (Point2D)points.get(0));
        endArrowHead.draw(canvas, (Point2D)points.get(points.size() - 2),
                (Point2D)points.get(points.size() - 1));


        drawString(canvas, (Point2D)points.get(1), (Point2D)points.get(0),
                startArrowHead, startLabel, false);
        drawString(canvas, (Point2D)points.get(points.size() / 2 - 1),
                (Point2D)points.get(points.size() / 2),
                null, middleLabel, true);
        drawString(canvas, (Point2D)points.get(points.size() - 2),
                (Point2D)points.get(points.size() - 1),
                endArrowHead, endLabel, false);

    }

    /**
     Draws a string.
     @param canvas the graphics context
     @param p an endpoint of the segment along which to
     draw the string
     @param q the other endpoint of the segment along which to
     draw the string
     @param s the string to draw
     @param center true if the string should be centered
     along the segment
     */

    // Changing this method to non-static, may break things


    private void drawString(Canvas canvas, Point2D p, Point2D q, ArrowHead arrow, String s, boolean center)

    {
        if (s == null || s.length() == 0) return;
        /**
         label.setText("<html>" + s + "</html>");
         label.setFont(g2.getFont());
         Dimension d = label.getPreferredSize();
         label.setBounds(0, 0, d.width, d.height);
         */
        Rectangle2D b = getStringBounds(canvas, p, q, arrow, s, center);

        Paint paint1 = new Paint();
        paint1.setColor(Color.BLACK);
        paint1.setStyle(Paint.Style.FILL);
        paint1.setStrokeWidth(1);

        text.setText(s);
        text.draw(canvas, b, paint1);

        /**
         Color oldColor = g2.getColor();
         g2.setColor(g2.getBackground());
         g2.fill(b);
         g2.setColor(oldColor);
         */

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        //Fills a space to draw text
        //canvas.drawRect(new Rect((int)b.getX(), (int)b.getY(), (int)b.getX() + (int)b.getWidth(), (int)b.getY() + (int)b.getHeight()),paint );

        // Draws the text. I think there is going to be problems scaling a lot of text. Fingers Crossed
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        //canvas.drawText(s,p.getX(), p.getY(), paint);
    }


    /**
     Computes the attachment point for drawing a string.
     @param canvas the graphics context
     @param p an endpoint of the segment along which to
     draw the string
     @param q the other endpoint of the segment along which to
     draw the string

     @param center true if the string should be centered
     along the segment
     @return the point at which to draw the string
     */
    private static Point2D getAttachmentPoint(Canvas canvas, Point2D p, Point2D q, ArrowHead arrow, Dimension d, boolean center)
    {
        final int GAP = 3;
        double xoff = GAP;
        double yoff = -GAP - d.getHeight();
        Point2D attach = q;
        if (center)
        {
            if (p.getX() > q.getX())
            {
                return getAttachmentPoint(canvas, q, p, arrow, d, center);
            }
            attach = new Point2D((p.getX() + q.getX()) / 2,
                    (p.getY() + q.getY()) / 2);
            if (p.getY() < q.getY())
                yoff =  - GAP - d.getHeight();
            else if (p.getY() == q.getY())
                xoff = -d.getWidth() / 2;
            else
                yoff = GAP;
        }
        else
        {
            if (p.getX() < q.getX())
            {
                xoff = -GAP - d.getWidth();
            }
            if (p.getY() > q.getY())
            {
                yoff = GAP;
            }
            if (arrow != null)
            {
                //Rectangle2D arrowBounds = arrow.getPath(p, q).getBounds2D(); replaced with code below
                RectF rect = new RectF();
                arrow.getPath(p,q).computeBounds(rect, true);
                Rectangle2D arrowBounds = new Rectangle2D(rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top);

                if (p.getX() < q.getX())
                {
                    xoff -= arrowBounds.getWidth();
                }
                else
                {
                    xoff += arrowBounds.getWidth();
                }
            }
        }
        return new Point2D(attach.getX() + xoff, attach.getY() + yoff);
    }

    /**
     Computes the extent of a string that is drawn along a line segment.
     @param canvas the graphics context
     @param p an endpoint of the segment along which to
     draw the string
     @param q the other endpoint of the segment along which to
     draw the string
     @param s the string to draw
     @param center true if the string should be centered
     along the segment
     @return the rectangle enclosing the string
     */
    private static Rectangle2D getStringBounds(Canvas canvas, Point2D p, Point2D q, ArrowHead arrow, String s, boolean center)
    {
        if (canvas == null) return new Rectangle2D();
        if (s == null || s.equals("")) return new Rectangle2D(q.getX(), q.getY(), 0, 0);

        //label.setText("<html>" + s + "</html>");
        //label.setFont(g2.getFont());
        //Dimension d = label.getPreferredSize();
        Rectangle2D d = new Rectangle2D(p.getX() - 10,p.getY() - 10,10,10); // NEED TO SET
        return d;
        //Point2D a = getAttachmentPoint(canvas, p, q, arrow, d, center);
        //return new Rectangle2D(a.getX(), a.getY(), d.getWidth(), d.getHeight());
        //return new Rectangle2D(); // Needed for return statement, so it wont crash, will likely cause problems!
    }

    public Rectangle2D getBounds(Canvas canvas)
    {
        ArrayList points = getPoints();
        Rectangle2D r = super.getBounds(canvas);
        r.add(getStringBounds(canvas,
                (Point2D) points.get(1), (Point2D) points.get(0),
                startArrowHead, startLabel, false));
        r.add(getStringBounds(canvas,
                (Point2D) points.get(points.size() / 2 - 1),
                (Point2D) points.get(points.size() / 2),
                null, middleLabel, true));
        r.add(getStringBounds(canvas,
                (Point2D) points.get(points.size() - 2),
                (Point2D) points.get(points.size() - 1),
                endArrowHead, endLabel, false));
        return r;
    }

    public Path getShape()
    {
        Path path = getSegmentPath();
        ArrayList points = getPoints();

        // Not sure if addPath is correct substitution for path.append, which is what the prof's code uses
        path.addPath(startArrowHead.getPath((Point2D)points.get(1),
                (Point2D)points.get(0)));
        path.addPath(endArrowHead.getPath((Point2D)points.get(points.size() - 2),
                (Point2D)points.get(points.size() - 1)));
        return path;
    }

    private Path getSegmentPath()
    {
        ArrayList points = getPoints();

        Path path = new Path();
        Point2D p = (Point2D) points.get(points.size() - 1);
        path.moveTo((float) p.getX(), (float) p.getY());
        for (int i = points.size() - 2; i >= 0; i--)
        {
            p = (Point2D) points.get(i);
            path.lineTo((float) p.getX(), (float) p.getY());
        }
        return path;
    }

    public Line2D getConnectionPoints()
    {
        ArrayList points = getPoints();
        return new Line2D((Point2D) points.get(0),
                (Point2D) points.get(points.size() - 1));
    }

    /**
     Gets the corner points of this segmented line edge
     @return an array list of Point2D objects, containing
     the corner points
     */
    public abstract ArrayList getPoints();

    protected Paint paintStyle;
    private ArrowHead startArrowHead;
    private ArrowHead endArrowHead;
    private String startLabel;
    private String middleLabel;
    private String endLabel;
    private MultiLineString text;

    //private static JLabel label = new JLabel();
}