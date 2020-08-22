package com.example.mobileviolet;

import android.app.Notification;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class ModifiedScrollView extends ScrollView{
    private float counter;
    private MotionEvent event;

    public ModifiedScrollView(Context context) {
        super(context);

    }

    public ModifiedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ModifiedScrollView(Context context, AttributeSet attrs, int defStyle) {
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