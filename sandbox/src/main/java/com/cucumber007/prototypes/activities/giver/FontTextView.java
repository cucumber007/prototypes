package com.cucumber007.prototypes.activities.giver;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.util.HashMap;
import java.util.Map;

public class FontTextView extends AppCompatTextView {

    private static final String FONT_ATTRIBUTE = "font";
    private static final String DEFAULT_FONT = "OpenSans.ttf";
    public static Map<String, Typeface> typefaceMap = new HashMap<>();


    public FontTextView(Context context) {
        super(context);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            boolean bold = false;

            for (int i = 0; i < attrs.getAttributeCount(); i++) {
                try {
                    if (attrs.getAttributeName(i).equals("textStyle")) {
                        bold = attrs.getAttributeIntValue(i, Typeface.NORMAL) == Typeface.BOLD;
                    }
                } catch (NumberFormatException e) {
                    try {
                        bold = attrs.getAttributeValue(i).equals("bold");
                    } catch (Exception e1) {
                        bold = false;
                    }
                }
            }


            boolean found = false;
            for (int i = 0; i < attrs.getAttributeCount(); i++) {
                if(attrs.getAttributeName(i).equals(FONT_ATTRIBUTE)) {
                    initFont(attrs.getAttributeValue(i));
                    found = true;
                }
            }

            if(!found) initFont(DEFAULT_FONT, bold);

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

    private void initFont(String filename, boolean bold) {
        try {
            if(!typefaceMap.containsKey(filename)) typefaceMap.put(filename, Typeface.createFromAsset(getContext().getAssets(), filename));
            setTypeface(typefaceMap.get(filename), bold ? Typeface.BOLD : Typeface.NORMAL);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private void initFont(String filename) {
        initFont(filename, false);
    }
}
