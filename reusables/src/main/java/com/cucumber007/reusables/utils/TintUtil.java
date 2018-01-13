package com.cucumber007.reusables.utils;

import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

public class TintUtil {

    public static void applyTint(ImageView imageView, int color) {
        Drawable normalDrawable = imageView.getDrawable();
        Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);
        DrawableCompat.setTint(wrapDrawable, color);
        imageView.setImageDrawable(wrapDrawable);
    }

}
