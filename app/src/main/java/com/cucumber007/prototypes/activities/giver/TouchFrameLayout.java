package com.cucumber007.prototypes.activities.giver;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class TouchFrameLayout extends FrameLayout {

    View dispatchable;

    public TouchFrameLayout(Context context) {
        super(context);
    }

    public TouchFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public TouchFrameLayout(Context context, AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (dispatchable != null) {
            dispatchable.dispatchTouchEvent(event);
        }
        return true;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (dispatchable != null) {
            dispatchable.dispatchTouchEvent(ev);
        }
        return true;
    }


    public void setDispatchable(View dispatchable) {
        this.dispatchable = dispatchable;
    }

}
