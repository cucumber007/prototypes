package com.cucumber007.prototypes.cases.state_and_callbacks;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import rx.Observable;

public class SampleDataProvider {

    public static String getString(int size) {
        byte byt = 'A';
        byte[] bytes = new byte[size];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = byt;
        }
        return new String(bytes);
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
