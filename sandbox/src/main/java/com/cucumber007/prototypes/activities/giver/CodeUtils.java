package com.cucumber007.prototypes.activities.giver;

public class CodeUtils {

    //import com.google.zxing.common.BitMatrix;

    /*public static Bitmap encodeAsBitmap(int size, String str, @ColorInt int color, @ColorInt int backgroundColor) {
        try {
            long time = System.currentTimeMillis();
            int WIDTH = size;
            int HEIGHT = size;
            BitMatrix result;
            Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 0); // default = 4
            try {
                result = new MultiFormatWriter().encode(str,
                        BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
            } catch (IllegalArgumentException iae) {
                // Unsupported format
                return null;
            }
            int w = result.getWidth();
            int h = result.getHeight();
            int[] pixels = new int[w * h];
            for (int y = 0; y < h; y++) {
                int offset = y * w;
                for (int x = 0; x < w; x++) {
                    pixels[offset + x] = result.get(x, y) ? color : backgroundColor;
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, WIDTH, 0, 0, w, h);
            //LogUtil.logDebug("QR create in: ",System.currentTimeMillis() - time);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }*/

}
