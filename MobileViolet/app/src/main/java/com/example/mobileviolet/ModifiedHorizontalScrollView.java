package com.example.mobileviolet;

import android.app.Notification;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class ModifiedHorizontalScrollView extends HorizontalScrollView{
    private float counter;
    private MotionEvent event;

    public ModifiedHorizontalScrollView(Context context) {
        super(context);

    }

    public ModifiedHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ModifiedHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                super.onTouchEvent(ev);
                break;

            case MotionEvent.ACTION_MOVE:
                this.requestDisallowInterceptTouchEvent(false);
            case MotionEvent.ACTION_CANCEL:
                super.onTouchEvent(ev);
                break;

            case MotionEvent.ACTION_UP:
                return false;

            default:  break;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {


        int action = ev.getActionMasked();

        switch (action) {

            case MotionEvent.ACTION_MOVE:
                return true;
        }

        return super.onTouchEvent(event);
    }
    public void setCount(float count)
    {
        counter = count;
    }


}