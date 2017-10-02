package com.cucumber007.reusables.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class DpUtil {

    //todo test
    public static float dpToPx(float dp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static float pxToDp(float px, Context context) {
        return px / ((float)context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

}
