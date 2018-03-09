package com.cucumber007.prototypes.activities.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.view.View;

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

    public void execute(View view, Runnable onBlurCompleteListener) {
        if (mState.equals(State.READY)) {
            mState = State.EXECUTING;
            sCacheBlurBehindAndExecuteTask =
                    new CacheBlurBehindAndExecuteTask(view, onBlurCompleteListener);
            sCacheBlurBehindAndExecuteTask.execute();
        }
    }

    public void setBackground(View view) {
        if (IMAGE_CACHE.size() != 0) {
            final BitmapDrawable bd = new BitmapDrawable(view.getContext().getResources(),
                    IMAGE_CACHE.get(KEY_CACHE_BLURRED_BACKGROUND_IMAGE));

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                //noinspection deprecation
                view.setBackgroundDrawable(bd);
            } else {
                view.setBackground(bd);
            }

            IMAGE_CACHE.remove(KEY_CACHE_BLURRED_BACKGROUND_IMAGE);
            sCacheBlurBehindAndExecuteTask = null;
        }
    }

    private class CacheBlurBehindAndExecuteTask extends AsyncTask<Void, Void, Void> {
        private View mView;
        private final Runnable mOnBlurCompleteListener;

        private Bitmap mImage;

        public CacheBlurBehindAndExecuteTask(View view, Runnable onBlurCompleteListener) {
            this.mView = view;
            this.mOnBlurCompleteListener = onBlurCompleteListener;
        }

        @Override protected void onPreExecute() {
            super.onPreExecute();

            final Bitmap bitmap = Bitmap.createBitmap(mView.getWidth(), mView.getHeight(),
                    Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(bitmap);
            mView.draw(canvas);

            mImage = bitmap;
        }

        @Override protected Void doInBackground(Void... params) {
            final Bitmap blurredBitmap = FastBlur.superBlur(mImage);
            IMAGE_CACHE.put(KEY_CACHE_BLURRED_BACKGROUND_IMAGE, blurredBitmap);

            return null;
        }

        @Override protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mView = null;

            mOnBlurCompleteListener.run();

            mState = State.READY;
        }
    }
}

