package com.cucumber007.reusables.camera;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cucumber007.reusables.R;
import com.cucumber007.reusables.listeners.LoadingListener;
import com.cucumber007.reusables.utils.logging.LogUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class BaseCameraPresenter {

    public static final int IMAGE_QUALITY_LIMIT_MEGAPIXELS = 2;

    private int cameraIndex = -1;
    private boolean previewStarted = false;
    private boolean surfaceCreated = false;
    private SurfaceHolder.Callback callback;
    @Nullable private Camera camera;
    private SurfaceHolder surfaceHolder;
    private Activity context;
    private SurfaceView preview;
    private LoadingListener loadingListener;
    private float currentAspect;
    private int currentOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    private boolean flashLightEnabled = false;
    private int cameraOrientation;
    private boolean cameraReady = false;
    private RotationDetector rotationDetector;


    public BaseCameraPresenter(Activity activity, SurfaceView preview, LoadingListener loadingListener
    ) {
        this.context = activity;
        this.preview = preview;
        this.loadingListener = loadingListener;
        surfaceHolder = preview.getHolder();
    }

    public void start() {
        callback = new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                onSurfaceCreated();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {}
        };
        surfaceHolder.addCallback(callback);
        preview.setVisibility(View.VISIBLE);

        rotationDetector = new RotationDetector(context, this::rotate);
    }

    public void resume() {
        if(preview.getVisibility() == View.GONE) preview.setVisibility(View.VISIBLE);
        if(camera == null) loadCamera(getCameraIndex());
        rotationDetector.enable();
    }

    public void pause() {
        stopPreview();
        releaseCamera();
        preview.setVisibility(View.GONE);
        rotationDetector.disable();
    }

    public void stop() {
        if (surfaceHolder != null) {
            surfaceHolder.removeCallback(callback);
        }
        if(preview.getVisibility() == View.VISIBLE) preview.setVisibility(View.GONE);
    }

    protected void rotate(int orientation) {
        currentOrientation = orientation;
    }

    protected void onSurfaceCreated() {
        surfaceCreated = true;
        if(camera == null) loadCamera(getCameraIndex());
        preparePreview();
    }

    private void loadCamera(int cameraIndex) {
        try {
            camera = Camera.open(getCameraIndex());
        } catch (RuntimeException e) {
            Toast.makeText(context, context.getResources().getString(R.string.camera_error), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            try {
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            } catch (Exception e1) {
                Toast.makeText(context, context.getResources().getString(R.string.camera_error), Toast.LENGTH_SHORT).show();
                e1.printStackTrace();
            }
        }
    }

    private void preparePreview() {
        //Log.d("cutag", "preparePreview");
        try {
            Camera.Parameters params = camera.getParameters();
            String desiredFocus = Camera.Parameters.FOCUS_MODE_AUTO;
            if(params.getSupportedFocusModes().contains(desiredFocus)) {
                params.setFocusMode(desiredFocus);
                camera.setParameters(params);
            }

            camera.setPreviewDisplay(surfaceHolder);
            Camera.Parameters chosenParameters = camera.getParameters();
            Camera.Size previewSize = chosenParameters.getSupportedPreviewSizes().get(0);
            Camera.Size pictureSize = chosenParameters.getSupportedPictureSizes().get(0);
            chosenParameters.setPreviewSize(previewSize.width, previewSize.height);
            chosenParameters.setPictureSize(pictureSize.width, pictureSize.height);

            //set preview
            boolean previewFound = false;
            for (int i = 0; i < chosenParameters.getSupportedPreviewSizes().size(); i++) {
                Camera.Size size = chosenParameters.getSupportedPreviewSizes().get(i);
                float aspect = (float) size.width / size.height;
                if (aspect < 1.52f) {
                    if(previewFound && size.width < chosenParameters.getPreviewSize().width) break;
                    else {
                        chosenParameters.setPreviewSize(size.width, size.height);
                        currentAspect = aspect;
                        previewFound = true;
                    }
                }
            }

            //set size
            boolean pictureFound = false;
            for (int i = 0; i < chosenParameters.getSupportedPictureSizes().size(); i++) {
                Camera.Size size = chosenParameters.getSupportedPictureSizes().get(i);
                float localAspect = (float) size.width / size.height;
                if (size.width*size.height <= IMAGE_QUALITY_LIMIT_MEGAPIXELS*1000000 && localAspect == currentAspect) {
                    if(pictureFound && size.width < chosenParameters.getPictureSize().width) break;
                    else {
                        chosenParameters.setPictureSize(size.width, size.height);
                        camera.setParameters(chosenParameters);
                        pictureFound = true;
                    }
                }
            }

            if(!pictureFound) {
                chosenParameters.setPictureSize(chosenParameters.getPreviewSize().width, chosenParameters.getPreviewSize().height);
            }
            if(getCameraIndex() == Camera.CameraInfo.CAMERA_FACING_BACK && flashLightEnabled) chosenParameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
            camera.setParameters(chosenParameters);

            cameraOrientation = setCameraDisplayOrientation(context, getCameraIndex(), camera);

            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                ViewGroup.LayoutParams lp = preview.getLayoutParams();
                lp.width = preview.getWidth();
                lp.height = (int) (preview.getWidth() * currentAspect);
                preview.setLayoutParams(lp);
            } else {
                ViewGroup.LayoutParams lp = preview.getLayoutParams();
                lp.height = preview.getHeight();
                lp.width = (int) (preview.getHeight() * currentAspect);
                preview.setLayoutParams(lp);
            }

            camera.setOneShotPreviewCallback((data, camera) -> {
                cameraReady();
            });
            startPreview();

        } catch (IOException | RuntimeException e) {
            Toast.makeText(context, context.getResources().getString(R.string.camera_error), Toast.LENGTH_SHORT).show();
            LogUtil.logError(e);
        }
    }

    public static int setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
        return result;
    }

    private void releaseCamera() {
        if (camera != null) camera.release();
        camera = null;
    }

    private void cameraReady() {
        cameraReady = true;
        onCameraReady();
    }

    protected void onCameraReady() {}

    private String sizeToString(Camera.Size size) {
        return size.width + " X "+size.height;
    }

    protected void changeFlashlightStatus(boolean status) {
        flashLightEnabled = status;
        if (getCameraIndex() == Camera.CameraInfo.CAMERA_FACING_BACK) {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(status ? Camera.Parameters.FLASH_MODE_ON : Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
        }
    }

    public void makePhoto(Camera.PictureCallback callback) {
        try {
            if (camera == null) {
                Toast.makeText(context, context.getResources().getString(R.string.camera_error), Toast.LENGTH_SHORT).show();
                return;
            }
            camera.getParameters().setFocusAreas(null);
            camera.autoFocus((success, cam) -> {
                onStartTakingPicture();

                try {
                    camera.takePicture(null, null, null, callback);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            });
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void makePhoto(BitmapCallback callback) {
        makePhoto((data, camera) -> {

            new AsyncTask<Void, Void, Void>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    getLoadingListener().onStartLoading();
                }

                @Override
                protected Void doInBackground(Void... params) {
                    Bitmap bitmap = prepareBitmap(data);
                    getContext().runOnUiThread(() -> callback.onPictureTaken(bitmap));
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    getLoadingListener().onStopLoading();
                }
            }.execute();
        });
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

    protected void onStartTakingPicture() {}

    public void focus(float pointX, float pointY, Runnable callback) {
        if (camera != null) {
            try {
                    if (getCameraIndex() == Camera.CameraInfo.CAMERA_FACING_BACK) {
                        List<Camera.Area> list = new ArrayList<>();
                        int rectX = (int)(pointX / preview.getMeasuredWidth() * 2000)-1000;
                        int rectY = -((int)(pointY / preview.getMeasuredHeight() * 2000)-1000);
                        //sprava verh
                        Rect rect = new Rect(
                                rectY-100 < -1000 ? rectY : rectY-100,
                                rectX-100 < -1000 ? rectX : rectX-100,
                                rectY+100 > 1000 ? rectY : rectY+100,
                                rectX+100 > 1000 ? rectX : rectX+100
                        );
                        list.add(new Camera.Area(rect, 1));
                        Camera.Parameters parameters = camera.getParameters();
                        parameters.setFocusAreas(list);
                        parameters.setMeteringAreas(list);
                        camera.setParameters(parameters);

                        camera.autoFocus((success, camera) -> {
                            callback.run();
                        });
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void changeCamera(int cameraIndex) {
        setCameraIndex(cameraIndex);
        stopPreview();
        releaseCamera();
        loadCamera(cameraIndex);
        changeFlashlightStatus(false);
        preparePreview();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters / Setters
    ///////////////////////////////////////////////////////////////////////////

    public int getCameraIndex() {
        if(cameraIndex == -1) cameraIndex = getDefaultCameraIndex();
        return cameraIndex;
    }

    public void setCameraIndex(int cameraIndex) {
        this.cameraIndex = cameraIndex;
    }

    protected int getDefaultCameraIndex() {
        return Camera.CameraInfo.CAMERA_FACING_BACK;
    }

    public SurfaceView getPreview() {
        return preview;
    }

    public Activity getContext() {
        return context;
    }

    public boolean isCameraReady() {
        return cameraReady;
    }

    public int getCurrentOrientation() {
        return currentOrientation;
    }

    public void setCurrentOrientation(int currentOrientation) {
        this.currentOrientation = currentOrientation;
    }

    private void startPreview() {
        camera.startPreview();
        previewStarted = true;
    }

    private void stopPreview() {
        if (camera != null) camera.stopPreview();
        previewStarted = false;
    }

    public boolean isFlashLightEnabled() {
        return flashLightEnabled;
    }

    public LoadingListener getLoadingListener() {
        return loadingListener;
    }

    public float getCurrentAspect() {
        return currentAspect;
    }

    public int getCameraOrientation() {
        return cameraOrientation;
    }

    @Nullable
    public Camera getCamera() {
        return camera;
    }

    public interface PictureCallback {
        void onPictureTaken(byte[] data);
    }

    public  interface BitmapCallback {
        void onPictureTaken(Bitmap bitmap);
    }
}
