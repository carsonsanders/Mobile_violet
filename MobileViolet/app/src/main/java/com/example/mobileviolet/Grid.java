package com.example.mobileviolet;

/**
 * Created by ginge_000 on 4/24/2017.
 */

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 A grid to which points and rectangles can be "snapped". The
 snapping operation moves a point to the nearest grid point.
 */
public class Grid
{
     /**
      Constructs a grid with no grid points.
      */
     public Grid()
     {
         setGrid(0,0);
         blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);
     }

     /**
      Sets the grid point distances in x- and y-direction
      @param x the grid point distance in x-direction
      @param y the grid point distance in y-direction
      */
     public void setGrid(double x, double y)
     {
          gridx = x;
          gridy = y;
     }

     /**
      Draws this grid inside a rectangle.
      @param  canvas cavas context
      @param bounds the bounding rectangle
      */
     public void draw(Canvas canvas, Rectangle2D bounds)
     {

          for (double x = bounds.getX(); x < bounds.getMaxX(); x += gridx)
               canvas.drawLine((float)x, (float)bounds.getY(), (float)x, (float)bounds.getMaxY(),blackPaint );
          for (double y = bounds.getY(); y < bounds.getMaxY(); y += gridy)
               canvas.drawLine((float)bounds.getX(), (float)y, (float)bounds.getMaxX(), (float)y, blackPaint);
     }

     /**
      Snaps a point to the nearest grid point
      @param p the point to snap. After the call, the
      coordinates of p are changed so that p falls on the grid.
      */
     public void snap(Point2D p)
     {
          double x;
          if (gridx == 0)
               x = p.getX();
          else
               x = Math.round(p.getX() / gridx) * gridx;
          double y;
          if (gridy == 0)
               y = p.getY();
          else
               y = Math.round(p.getY() / gridy) * gridy;

          p.setLocation(x, y);
     }

     /**
      Snaps a rectangle to the nearest grid points
      @param r the rectangle to snap. After the call, the
      coordinates of r are changed so that all of its corners
      falls on the grid.
      */
     public void snap(Rectangle2D r)
     {
          double x;
          double w;
          if (gridx == 0)
          {
               x = r.getX();
               w = r.getWidth();
          }
          else
          {
               x = Math.round(r.getX() / gridx) * gridx;
               w = Math.ceil(r.getWidth() / (2 * gridx)) * (2 * gridx);
          }
          double y;
          double h;
          if (gridy == 0)
          {
               y = r.getY();
               h = r.getHeight();
          }
          else
          {
               y = Math.round(r.getY() / gridy) * gridy;
               h = Math.ceil(r.getHeight() / (2 * gridy)) * (2 * gridy);
          }

          r.setFrame(x, y, w, h);
     }

     private Paint blackPaint = new Paint();
     private double gridx;
     private double gridy;
}