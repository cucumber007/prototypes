package com.cucumber007.prototypes.activities.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MultilineAlignTextView extends TextView {

    ViewTreeObserver.OnGlobalLayoutListener listener;

    public MultilineAlignTextView(Context context) {
        super(context);
    }

    public MultilineAlignTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MultilineAlignTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        View parent = ((View)getParent());

        listener = () -> {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)getLayoutParams();
            float max = 0;
            for (int i = 0; i < getLineCount(); i++) {
                float line = getLayout().getLineMax(i);
                if(line > max) max = line;
            }
            lp.width = (int)Math.ceil(max);
            lp.setMargins((int)((parent.getWidth()-lp.width)/2.),0,0,lp.bottomMargin);
            lp.addRule(RelativeLayout.ALIGN_RIGHT, RelativeLayout.TRUE);
            setLayoutParams(lp);
            getViewTreeObserver().removeOnGlobalLayoutListener(listener);
            Log.d("cutag", ""+max);
        };
        parent.getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }
}
