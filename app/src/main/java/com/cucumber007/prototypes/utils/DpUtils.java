package com.cucumber007.prototypes.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class DpUtils {

    //todo test
    public static float dpToPx(float dp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
    }

    public static float pxToDp(float px, Context context) {
        return px * ( context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

}
