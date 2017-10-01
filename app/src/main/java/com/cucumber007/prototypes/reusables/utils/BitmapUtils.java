package com.cucumber007.prototypes.reusables.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import java.io.ByteArrayOutputStream;

public class BitmapUtils {

    public static Bitmap getRoundedCornerBitmap(int containerWidth, int containerHeight, Bitmap bitmap, int radius) {
        Bitmap crop;
        if (containerWidth >= containerHeight) {
            int targetHeight = (int) (bitmap.getWidth() * containerHeight / (float) containerWidth);
            crop = Bitmap.createBitmap(
                    bitmap,
                    0,
                    (bitmap.getHeight() - targetHeight) / 2,
                    bitmap.getWidth(),
                    targetHeight
            );
        } else {
            int targetWidth = (int) (bitmap.getHeight() * containerWidth / (float) containerHeight);
            crop = Bitmap.createBitmap(
                    bitmap,
                    (bitmap.getWidth() - targetWidth) / 2,
                    0,
                    targetWidth,
                    bitmap.getHeight()
            );
        }

        Bitmap output = Bitmap.createBitmap(crop.getWidth(), crop.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff111111;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, output.getWidth(), output.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = radius;

        paint.setAntiAlias(true);
        //canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(crop, rect, rect, paint);

        return output;
    }

    public static byte[] bitmapToBytes(Bitmap bitmap) {
        return bitmapToBytes(bitmap, Bitmap.CompressFormat.JPEG, 90);
    }

    public static byte[] bitmapToBytes(Bitmap bitmap, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(compressFormat, quality, stream);
        return stream.toByteArray();
    }



}
