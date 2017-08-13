package com.cucumber007.prototypes.reusables.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class FontTextView extends TextView {

    private static final String FONT_ATTRIBUTE = "font";
    public static Map<String, Typeface> typefaceMap = new HashMap<>();


    public FontTextView(Context context) {
        super(context);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {

            for (int i = 0; i < attrs.getAttributeCount(); i++) {
                if(attrs.getAttributeName(i).equals(FONT_ATTRIBUTE)) {
                    initFont(attrs.getAttributeValue(i));
                }
            }

            /*TypedArray attrsArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomToggleView);

            try {
                altDrawable = attrsArray.getDrawable(R.styleable.CustomToggleView_alt_drawable);
                tapQuantity = attrsArray.getInteger(R.styleable.CustomToggleView_tap_quantity, 1);
            } finally {
                attrsArray.recycle();
            }

            TypedArray defAttrsArray = getContext().obtainStyledAttributes(attrs, new int[] {src});
            src = defAttrsArray.getDrawable(0);
            defAttrsArray.recycle();

            super.setOnClickListener(view -> {
                if(tapTimes >= tapQuantity) {
                    setImageDrawable(toggled ? altDrawable : src);
                    toggled = !toggled;
                    tapTimes = 0;
                }
                tapTimes++;
            });*/
        }
    }

    private void initFont(String filename) {
        try {
            if(!typefaceMap.containsKey(filename)) typefaceMap.put(filename, Typeface.createFromAsset(getContext().getAssets(), filename));
            setTypeface(typefaceMap.get(filename));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
