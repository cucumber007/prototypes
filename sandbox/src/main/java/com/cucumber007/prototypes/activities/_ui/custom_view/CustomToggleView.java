package com.cucumber007.prototypes.activities._ui.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.cucumber007.prototypes.R;

public class CustomToggleView extends ImageView {

    private Drawable altDrawable;
    private Drawable src;
    private boolean toggled = false;
    private int tapQuantity = 1;
    private int tapTimes = 0;

    public CustomToggleView(Context context) {
        super(context);
        init(null);
    }

    public CustomToggleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomToggleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attrsArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomToggleView);

            try {
                altDrawable = attrsArray.getDrawable(R.styleable.CustomToggleView_alt_drawable);
                tapQuantity = attrsArray.getInteger(R.styleable.CustomToggleView_tap_quantity, 1);
            } finally {
                attrsArray.recycle();
            }

            TypedArray defAttrsArray = getContext().obtainStyledAttributes(attrs, new int[] {android.R.attr.src});
            src = defAttrsArray.getDrawable(0);
            defAttrsArray.recycle();

            super.setOnClickListener(view -> {
                if(tapTimes >= tapQuantity) {
                    setImageDrawable(toggled ? altDrawable : src);
                    toggled = !toggled;
                    tapTimes = 0;
                }
                tapTimes++;
            });
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        //super.setOnClickListener(l);
    }
}
