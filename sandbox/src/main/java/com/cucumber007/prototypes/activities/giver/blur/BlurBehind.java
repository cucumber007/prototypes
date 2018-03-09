package com.cucumber007.prototypes.activities.giver.blur;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by: Anton Shkurenko (tonyshkurenko)
 * Project: Animations
 * Date: 6/11/16
 * Code style: SquareAndroid (https://github.com/square/java-code-styles)
 * Follow me: @tonyshkurenko
 */
public class BlurBehind {

    private static final String KEY_CACHE_BLURRED_BACKGROUND_IMAGE =
            "KEY_CACHE_BLURRED_BACKGROUND_IMAGE";

    private static final LruCache<String, Bitmap> IMAGE_CACHE = new LruCache<>(1);
    private static CacheBlurBehindAndExecuteTask sCacheBlurBehindAndExecuteTask;

    private enum State {
        READY,
        EXECUTING
    }

    private State mState = State.READY;

    private static final BlurBehind sInstance = new BlurBehind();

    public static BlurBehind getInstance() {
        return sInstance;
    }

    public void execute(View sourceView, Runnable onBlurCompleteListener) {
        if (mState.equals(State.READY)) {
            mState = State.EXECUTING;
            sCacheBlurBehindAndExecuteTask =
                    new CacheBlurBehindAndExecuteTask(sourceView, onBlurCompleteListener);
            sCacheBlurBehindAndExecuteTask.execute();
        }
    }

    public void execute(Bitmap bitmap, Runnable onBlurCompleteListener) {
        if (mState.equals(State.READY)) {
            mState = State.EXECUTING;
            sCacheBlurBehindAndExecuteTask =
                    new CacheBlurBehindAndExecuteTask(bitmap, onBlurCompleteListener);
            sCacheBlurBehindAndExecuteTask.execute();
        }
    }

    public void setBackground(View targetView) {
        if (IMAGE_CACHE.size() != 0) {
            final BitmapDrawable bd = new BitmapDrawable(targetView.getContext().getResources(),
                    IMAGE_CACHE.get(KEY_CACHE_BLURRED_BACKGROUND_IMAGE));

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                //noinspection deprecation
                targetView.setBackgroundDrawable(bd);
            } else {
                targetView.setBackground(bd);
            }

            IMAGE_CACHE.remove(KEY_CACHE_BLURRED_BACKGROUND_IMAGE);
            sCacheBlurBehindAndExecuteTask = null;
        }
    }

    public void setSrc(ImageView targetView) {
        if (IMAGE_CACHE.size() != 0) {
            final BitmapDrawable bd = new BitmapDrawable(targetView.getContext().getResources(),
                    IMAGE_CACHE.get(KEY_CACHE_BLURRED_BACKGROUND_IMAGE));

            targetView.setImageDrawable(bd);

            IMAGE_CACHE.remove(KEY_CACHE_BLURRED_BACKGROUND_IMAGE);
            sCacheBlurBehindAndExecuteTask = null;
        }
    }

    private class CacheBlurBehindAndExecuteTask extends AsyncTask<Void, Void, Void> {
        private View sourceView;
        private final Runnable mOnBlurCompleteListener;
        private Bitmap bitmap;

        public CacheBlurBehindAndExecuteTask(View sourceView, Runnable onBlurCompleteListener) {
            this.sourceView = sourceView;
            this.mOnBlurCompleteListener = onBlurCompleteListener;
        }

        public CacheBlurBehindAndExecuteTask(Bitmap bitmap, Runnable onBlurCompleteListener) {
            this.bitmap = bitmap;
            this.mOnBlurCompleteListener = onBlurCompleteListener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (bitmap == null) {
                final Bitmap bitmap = Bitmap.createBitmap(sourceView.getWidth(), sourceView.getHeight(),
                        Bitmap.Config.ARGB_8888);
                final Canvas canvas = new Canvas(bitmap);
                sourceView.draw(canvas);
                this.bitmap = bitmap;
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            final Bitmap blurredBitmap = FastBlur.superBlur(bitmap);
            IMAGE_CACHE.put(KEY_CACHE_BLURRED_BACKGROUND_IMAGE, blurredBitmap);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            sourceView = null;
            mOnBlurCompleteListener.run();
            mState = State.READY;
        }
    }
}

