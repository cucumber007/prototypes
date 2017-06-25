package com.cucumber007.prototypes.activities.giver;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.activities.giver.objects.Checkin;
import com.cucumber007.prototypes.activities.giver.objects.Offer;
import com.cucumber007.prototypes.reusables.LoadingListener;

import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("deprecation")
public class GiverCameraPresenter extends BaseCameraPresenter {

    private Offer offer;
    private Checkin checkin;
    private final ImageView cameraFlashButton;
    private final ImageView cameraTimerButton;
    private ImageView overlayImage;
    private TextView timeText;
    private ImageView overlayBlack;
    private final ImageView focusImage;
    private final FrameLayout flashLayout;
    private ViewGroup cameraControlsLayout;
    private CameraControls cameraControls;
    private Bitmap overlayBitmap;
    private Bitmap overlayBitmapMirrored;
    private boolean overlayReady = false;
    private View cameraModeButton;
    private Timer timer;
    private boolean timerEnabled = false;
    private BrightnessUtil brightnessUtil;
    private ContentResolver resolver;


    public GiverCameraPresenter(
            Activity context,
            Offer offer,
            Checkin checkin,
            SurfaceView preview,
            View cameraModeButton,
            ImageView cameraFlashButton,
            ImageView cameraTimerButton,
            ImageView overlayImage,
            TextView timeText,
            ImageView overlayBlack,
            ImageView focusImage,
            FrameLayout flash,
            ViewGroup cameraControlsLayout,
            CameraControls cameraControls,
            LoadingListener loadingListener
    ) {
        super(context, preview, loadingListener);
        this.offer = offer;
        this.checkin = checkin;
        this.cameraModeButton = cameraModeButton;
        this.cameraFlashButton = cameraFlashButton;
        this.cameraTimerButton = cameraTimerButton;
        this.overlayImage = overlayImage;
        this.timeText = timeText;
        this.overlayBlack = overlayBlack;
        this.focusImage = focusImage;
        this.flashLayout = flash;
        this.cameraControlsLayout = cameraControlsLayout;
        this.cameraControls = cameraControls;

        resolver = getContext().getContentResolver();
        setCameraButtonsEnabled(false);
        getLoadingListener().onStartLoading();

        if (offer != null)
            Glide.with(context).load(offer.getMaskUrl()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            overlayBitmap = resource;
                            overlayBitmapMirrored = getMirroredBitmap(resource);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            onOverlayReady();
                        }
                    }.execute();
                }
            });
        else {
            overlayBitmap = getDefaultMask();
            overlayBitmapMirrored = getDefaultMask();
            onOverlayReady();
        }

        if (Camera.getNumberOfCameras() <= 1) {
            cameraModeButton.setVisibility(View.GONE);
            setCameraIndex(Camera.CameraInfo.CAMERA_FACING_BACK);
        }

        //if(cameraIndex != Camera.CameraInfo.CAMERA_FACING_BACK) cameraFlashButton.setVisibility(View.GONE);
    }

    @Override
    public void pause() {
        super.pause();
        disableTimer();
        setTimerViewEnabled(false);
    }

    @Override
    protected void rotate(int orientation) {
        super.rotate(orientation);
        float size = 5;//getContext().getResources().getDimension(R.dimen.camera_control_size);
        float flashSize = 5;//getContext().getResources().getDimension(R.dimen.camera_flash_size);
        float from = RotationDetector.getRangeMiddleMirror(getCurrentOrientation());
        float to = RotationDetector.getRangeMiddleMirror(orientation);

        /*Animation animation = new RotateAnimation(
                from > 180 ? from - 360 : from,
                to > 180 ? to - 360 : to,
                size/2,
                size/2
        );

        animation.setDuration(300);
        animation.setFillAfter(true);
        animation.setRepeatCount(0);

        //if(cameraIndex == Camera.CameraInfo.CAMERA_FACING_BACK) {
        Animation flashAnimation = new RotateAnimation(
                from > 180 ? from - 360 : from,
                to > 180 ? to - 360 : to,
                flashSize/2,
                flashSize/2
        );*/

        /*flashAnimation.setDuration(300);
        flashAnimation.setFillAfter(true);
        flashAnimation.setRepeatCount(0);
        cameraFlashButton.startAnimation(flashAnimation);*/

           /* } else {
                cameraFlashButton.clearAnimation();
                //cameraFlashButton.setVisibility(View.GONE);
            }*/

        cameraModeButton.animate().rotation(RotationDetector.getRangeMiddleMirror(orientation));
        cameraFlashButton.animate().rotation(RotationDetector.getRangeMiddleMirror(orientation));

        //cameraTimerButton.startAnimation(animation);

        overlayImage.setRotation(RotationDetector.getRangeMiddleMirror(orientation));
        timeText.setRotation(RotationDetector.getRangeMiddleMirror(orientation));
    }

    @Override
    protected void onSurfaceCreated() {
        super.onSurfaceCreated();
        //prepareOverlay();
    }

    @Override
    protected int getDefaultCameraIndex() {
        return offer.getDefaultCamera() == 1 ? Camera.CameraInfo.CAMERA_FACING_BACK : Camera.CameraInfo.CAMERA_FACING_FRONT;
    }

    private void setCameraButtonsEnabled(boolean enabled) {
        cameraControls.setControlsEnabled(enabled);
    }

    private Bitmap getMirroredBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true); //long
    }

    @Override
    protected void onCameraReady() {
        if(isCameraReady() && overlayReady) onAllReady();
    }

    private void onOverlayReady() {
        overlayReady = true;
        if(isCameraReady() && overlayReady) onAllReady();
    }

    private void onAllReady() {
        overlayImage.setImageBitmap(getCameraIndex() == Camera.CameraInfo.CAMERA_FACING_FRONT ?
                        overlayBitmapMirrored : overlayBitmap);
        getLoadingListener().onStopLoading();
        overlayBlack.setVisibility(View.INVISIBLE);
        setCameraButtonsEnabled(true);
    }

    private Bitmap getDefaultMask() {
        return BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ic_launcher);
    }

    @Deprecated
    private void prepareOverlay() {
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            int cameraHeight = getPreview().getMeasuredWidth();
            overlayImage.getLayoutParams().height = cameraHeight;
            RelativeLayout.LayoutParams rlp = ((RelativeLayout.LayoutParams) cameraControlsLayout.getLayoutParams());
            //rlp.setMargins(0, cameraHeight+(int)getContext().getResources().getDimension(R.dimen.camera_top_margin), 0, 0);
            cameraControlsLayout.setLayoutParams(rlp);
            cameraControlsLayout.requestLayout();
        } else {
            int cameraWidth = getPreview().getMeasuredHeight();
            overlayImage.getLayoutParams().width = cameraWidth;
            RelativeLayout.LayoutParams rlp = ((RelativeLayout.LayoutParams) cameraControlsLayout.getLayoutParams());
            rlp.setMargins(cameraWidth, 0, 0, 0);
            cameraControlsLayout.setLayoutParams(rlp);
            cameraControlsLayout.requestLayout();
        }
    }

    @Override
    public void changeFlashlightStatus(boolean status) {
        super.changeFlashlightStatus(status);
        //cameraFlashButton.setImageDrawable(getContext().getResources().getDrawable(status ? R.drawable.icon_flash_on : R.drawable.icon_flash_off));
    }

    public void makePhoto(BitmapCallback callback) {
        super.makePhoto((data, camera) -> {

            if(getCameraIndex() == Camera.CameraInfo.CAMERA_FACING_FRONT && isFlashLightEnabled()) {
                flashLayout.setVisibility(View.GONE);
                brightnessUtil.revertDefault();
            }

            new AsyncTask<Void, Void, Void>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    getLoadingListener().onStartLoading();
                }

                @Override
                protected Void doInBackground(Void... params) {
                    Bitmap bitmap = prepareBitmap(data);
                    Bitmap overlayed = bitmapOverlay(overlayBitmap, bitmap);
                    getContext().runOnUiThread(() -> callback.onPictureTaken(overlayed));
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    getLoadingListener().onStopLoading();
                }
            }.execute();
        });
        setCameraButtonsEnabled(false);
        focusImage.setVisibility(View.GONE);
    }

    public void makePhotoWithTimer(BitmapCallback callback) {
        setCameraButtonsEnabled(false);
        startTimer(new TimerCallback() {
            @Override
            public void tick(int secondsLeft) {
                timeText.setText("" + secondsLeft);
            }

            @Override
            public void over() {
                setTimerViewEnabled(false);
                makePhoto(callback);
            }
        });
    }

    @Override
    protected void onStartTakingPicture() {
        if(getCameraIndex() == Camera.CameraInfo.CAMERA_FACING_FRONT && isFlashLightEnabled()) {
            flashLayout.setVisibility(View.VISIBLE);
            brightnessUtil = new BrightnessUtil(resolver);
            brightnessUtil.setMaxBrightness();
        }
    }

    protected void onPictureTaken(byte[] data) {

    }

    public void createTimer() {
        if(!timerEnabled)
            setTimerViewEnabled(true);
        else {
            setTimerViewEnabled(false);
            disableTimer();
            setCameraButtonsEnabled(true);
        }
    }

    public void startTimer(TimerCallback callback) {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                private int secondsLeft = 10;

                @Override
                public void run() {
                    getContext().runOnUiThread(() -> {
                        if (secondsLeft > 0) {
                            callback.tick(secondsLeft);
                            secondsLeft--;
                        }
                        else {
                            callback.over();
                            disableTimer();
                        }
                    });
                }
            }, 0, 1000);
        }
    }

    private void disableTimer() {
        if (timer != null) {
            timeText.setVisibility(View.INVISIBLE);
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }

    private void setTimerViewEnabled(boolean enabled) {
        timerEnabled = enabled;
        if (enabled) {
            timeText.setVisibility(View.VISIBLE);
            //cameraTimerButton.setImageDrawable(getContext().getResources().getDrawable(R.drawable.timer_on));
        } else {
            timeText.setVisibility(View.INVISIBLE);
            timeText.setText(""+10);
            //cameraTimerButton.setImageDrawable(getContext().getResources().getDrawable(R.drawable.timer_off));
        }
    }

    public static Bitmap bitmapOverlay(Bitmap up, Bitmap down) {
        Bitmap bmOverlay = Bitmap.createBitmap(down.getWidth(), down.getHeight(), down.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(down, new Matrix(), null);
        canvas.drawBitmap(Bitmap.createScaledBitmap(up, down.getWidth(), down.getHeight(), true), new Matrix(), null);
        return bmOverlay;
    }

    private Bitmap prepareBitmap(byte[] data) {
        //despite GC, possible Out of memory error
        System.gc();
        Bitmap photo = BitmapFactory.decodeByteArray(data, 0, data.length); //mid

        //workaround against stretching
        if (getCurrentAspect() == 1)
            photo = Bitmap.createScaledBitmap(photo, photo.getWidth(), photo.getWidth(), false);

        boolean frontalCamera = getCameraIndex() == Camera.CameraInfo.CAMERA_FACING_FRONT;
        int coef = 0;
        if(getCurrentOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) coef = 0;
        else if(getCurrentOrientation() == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) coef = frontalCamera ? 90 : -90;
        else if(getCurrentOrientation() == ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) coef = frontalCamera ? 180 : 180;
        else if(getCurrentOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) coef = frontalCamera ? -90 : 90;
        Matrix matrix = new Matrix();
        if (!frontalCamera)
            matrix.postRotate(getCameraOrientation() + coef);
        else {
            matrix.postRotate(getCameraOrientation() + 180 + coef);
            //matrix.preScale(1, -1);
        }

        if(frontalCamera)
            photo = Bitmap.createBitmap(photo, getCameraOrientation() == 90 ?
                            photo.getWidth()-photo.getHeight()
                            : 0, 0, photo.getHeight(),
                    photo.getHeight(),matrix, true); //long
        else
            photo = Bitmap.createBitmap(photo,
                    getCameraOrientation() == 90 ? 0 : photo.getWidth()-photo.getHeight(),
                    0, photo.getHeight(), photo.getHeight(), matrix, true);
        return photo;
    }

    @Override
    public void changeCamera(int cameraIndex) {
        super.changeCamera(cameraIndex);

        overlayBlack.setVisibility(View.VISIBLE);

        //todo if no flash device
        if(cameraIndex != Camera.CameraInfo.CAMERA_FACING_BACK) {
            cameraFlashButton.clearAnimation();
            //cameraFlashButton.setVisibility(View.GONE);
        }
        //else cameraFlashButton.setVisibility(View.VISIBLE);
    }

    public Bitmap getOverlayBitmap() {
        return overlayBitmap;
    }

    public boolean isTimerEnabled() {
        return timerEnabled;
    }

    public boolean isOverlayReady() {
        return overlayReady;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Interfaces
    ///////////////////////////////////////////////////////////////////////////

    public interface CameraControls {
        void setControlsEnabled(boolean enabled);
    }

    private interface TimerCallback {
        void tick(int secondsLeft);
        void over();
    }

    public  interface BitmapCallback {
        void onPictureTaken(Bitmap bitmap);
    }

}
