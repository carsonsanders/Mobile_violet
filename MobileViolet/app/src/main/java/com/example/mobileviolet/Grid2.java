package com.example.mobileviolet;
/*
Violet - A program for editing UML diagrams.

Copyright (C) 2002 Cay S. Horstmann (http://horstmann.com)

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 A grid to which points and rectangles can be "snapped". The
 snapping operation moves a point to the nearest grid point.
 */
public class Grid2
{
    /**
     Constructs a grid with no grid points.
     */
    public Grid2()
    {
        setGrid(0, 0);
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
     @param canvas the graphics context
     @param bounds the bounding rectangle
     */
    public void draw(Canvas canvas, Rect bounds)
    {
        Paint paint = new Paint();
        setGrid(30, 30);
        int PALE_BLUE = Color.rgb(230, 204, 230); //NOT PALE BLUE
        paint.setColor(PALE_BLUE);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);

        for (double x = 0; x < bounds.centerX() + bounds.width()/2; x += gridx)
            canvas.drawLine((float)x, bounds.centerY() - bounds.height()/2, (float)x, bounds.centerY() + bounds.height()/2, paint);
        for (double y = 0; y < bounds.centerY() + bounds.height()/2; y += gridy)
            canvas.drawLine(bounds.centerX() - bounds.width()/2, (float)y,bounds.centerX() + bounds.width()/2, (float)y, paint);

    }



    /**
     Snaps a point to the nearest grid point
     @param p the point to snap. After the call, the
     coordinates of p are changed so that p falls on the grid.
     */
    /** public void snap(Point2D p)
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
     */

    /**
     Snaps a rectangle to the nearest grid points
     // @param r the rectangle to snap. After the call, the
     coordinates of r are changed so that all of its corners
     falls on the grid.
     */


    /**
    public void snap(node n)
    {
        double x;
        double w;
        if (gridx == 0)
        {
            x = n.getBounds().centerX();
            w = n.getBounds().width();
        }
        else
        {
            x = Math.round((n.getBounds().centerX() / gridx) * gridx);
            w = Math.ceil(n.getBounds().width() / (2 * gridx)) * (2 * gridx);
        }
        double y;
        double h;
        if (gridy == 0)
        {
            y = n.getBounds().centerY();
            h = n.getBounds().height();
        }
        else
        {
            y = Math.round((n.getBounds().centerY()) / gridy) * gridy;
            h = Math.ceil(n.getBounds().height() / (2 * gridy)) * (2 * gridy);
        }

        n.setBounds(new Rect((int)(x-w/2), (int)(y-h/2), (int)(x + w/2), (int)(y + h/2)));
    }
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
            x = Math.round((r.getX() / gridx) * gridx);
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
            y = Math.round((r.getY() / gridy) * gridy);
            h = Math.ceil(r.getHeight() / (2 * gridy) * (2 * gridy));
        }

        r.setFrame(x, y, w, h);
    }

    private double gridx;
    private double gridy;
}
