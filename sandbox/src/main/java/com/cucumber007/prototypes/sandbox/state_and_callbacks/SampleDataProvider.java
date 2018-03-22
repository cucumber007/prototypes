package com.cucumber007.prototypes.sandbox.state_and_callbacks;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.cucumber007.prototypes.BuildConfig;
import com.cucumber007.prototypes.R;

import rx.Observable;

public class SampleDataProvider {

    public static String getString(int size) {
        StringBuilder stringBuilder = new StringBuilder(size);
        Observable.just('A').repeat(size).subscribe(stringBuilder::append);
        return stringBuilder.toString();
    }

    public static Bitmap getBitmap(int width, int height) {
        Paint paint = new Paint();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawARGB(255, 200, 200, 200);
        canvas.drawCircle(width/2, height/2, (float)(Math.min(width, height)*Math.random()), paint);
        return bitmap;
    }
}
