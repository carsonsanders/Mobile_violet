package com.example.mobileviolet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by phoenixnguyen on 4/27/17.
 */

public class MultiLineString implements Serializable {
    private static final long serialVersionUID = 2624829;
    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;
    public static final int LARGE = 3;
    public static final int NORMAL = 40;
    public static final int SMALL = 5;

    private static final int GAP = 20;

     //TextView is the Android equivalent of JLabel

    private String text;
    private int justification;
    private int size;
    private boolean underlined;
    private TextView label ;

    public MultiLineString()
    {
        text = "";
        justification = CENTER;
        size = NORMAL;
        underlined = false;


    }

    /**
     Sets the value of the text property.
     @param newValue the text of the multiline string
     */
    public void setText(String newValue) { text = newValue; /**setLabelText(); */ }
    /**
     Gets the value of the text property.
     @return the text of the multiline string
     */
    public String getText() { return text; }
    /**
     Sets the value of the justification property.
     @param newValue the justification, one of LEFT, CENTER,
     RIGHT
     */
    public void setJustification(int newValue) { justification = newValue; setLabelText(); }
    /**
     Gets the value of the justification property.
     @return the justification, one of LEFT, CENTER,
     RIGHT
     */
    public int getJustification() { return justification; }
    /**
     Gets the value of the underlined property.
     @return true if the text is underlined
     */
    public boolean isUnderlined() { return underlined; }
    /**
     Sets the value of the underlined property.
     @param newValue true to underline the text
     */
    public void setUnderlined(boolean newValue) { underlined = newValue; setLabelText(); }
    /**
     Sets the value of the size property.
     @param newValue the size, one of SMALL, NORMAL, LARGE
     */
    public void setSize(int newValue) { size = newValue; setLabelText(); }
    /**
     Gets the value of the size property.
     @return the size, one of SMALL, NORMAL, LARGE
     */
    public int getSize() { return size; }

    public String toString()
    {
        return text.replace('\n', '|');
    }

    private void setLabelText()
    {
        //label.setText(htmlText.toString());
        if (justification == LEFT) label.setGravity(Gravity.LEFT);
        else if (justification == CENTER) label.setGravity(Gravity.CENTER);
        else if (justification == RIGHT) label.setGravity(Gravity.RIGHT);
    }

    private View getLabel()
    {
        return label;
    }

    public void draw(Canvas canvas, Rectangle2D r, Paint paint)
    {
        Rect textBounds = new Rect();
        //paint.getTextBounds(getText(), 0, getText().length(), textBounds);
        //paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTextSize(MultiLineString.NORMAL);

        canvas.drawText(getText(), (float)r.getX() + GAP, (float)r.getCenterY(), paint);
        paint.setStrokeWidth(3);
        /*
        if (!(text.equals("")) || text == null) {
            canvas.drawLine((float) r.getX() + GAP, (float) (r.getCenterY() + getBounds(canvas).getHeight()/4),
                    (float) r.getMaxX() - GAP, (float) (float) (r.getCenterY() + getBounds(canvas).getHeight()/4), paint);
        }
        */
    }

    public Rectangle2D getBounds(Canvas canvas)
    {
        if (getText().length() == 0) return new Rectangle2D();
        // setLabelText();
        Paint paint = new Paint();
        paint.setTextSize(MultiLineString.NORMAL);
        Rect r = new Rect();
        paint.getTextBounds(getText(), 0, getText().length(), r);
        return new Rectangle2D(0, 0, r.width() + GAP, r.height());
    }

}