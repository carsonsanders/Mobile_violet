package com.example.mobileviolet;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phoenixnguyen on 5/10/17.
 */

public class ColorAdapter extends ArrayAdapter<ColorList.ColorIcon> {
    private Context context;
    private ColorList.ColorIcon[] colors;
    private int oldColor;


    public ColorAdapter(Context context, @LayoutRes int resource, @NonNull ColorList.ColorIcon[] colors) {
        super(context, resource, colors);
        this.context = context;
        this.colors = colors;
        oldColor = getOldColor();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {   // Ordinary view in Spinner, we use android.R.layout.simple_spinner_item
        TextView text = new TextView(context);
        text.setTextSize(20);
        text.setBackgroundColor(colors[position].getColor());
        return text;
    }

    public View getDropDownView(final int position, View convertView, ViewGroup parent)
    {   // Ordinary view in Spinner, we use android.R.layout.simple_spinner_item
        TextView text = new TextView(context);
        text.setBackgroundColor(colors[position].getColor());
        text.setTextSize(25);
        return text;
    }

    public void setOldColor(int newColor)
    {
        oldColor = newColor;
    }

    public int getOldColor()
    {
        return oldColor;
    }


}