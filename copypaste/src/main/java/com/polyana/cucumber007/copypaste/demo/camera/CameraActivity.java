package com.polyana.cucumber007.copypaste.demo.camera;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cucumber007.reusables.camera.CameraPresenter;
import com.polyana.cucumber007.copypaste.demo.photo_list.PhotoTransferManager;
import com.cucumber007.reusables.camera.RotationDetector;
import com.cucumber007.reusables.listeners.AbstractSimpleAnimatorListener;
import com.cucumber007.reusables.listeners.LoadingListener;
import com.polyana.cucumber007.copypaste.R;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class CameraActivity extends AppCompatActivity implements LoadingListener, CameraPresenter.CameraControls {

    @BindView(R.id.flash) FrameLayout flash;
    @BindView(R.id.surfaceView) SurfaceView surfaceView;
    @BindView(R.id.focus_image) ImageView focusImage;
    @BindView(R.id.overlayBlack) ImageView overlayBlack;
    @BindView(R.id.camera_mode_button) ImageView cameraModeButton;
    @BindView(R.id.camera_flash) ImageView cameraFlash;
    @BindView(R.id.shot_button) ImageView shotButton;
    @BindView(R.id.time) TextView time;
    @BindView(R.id.camera_controls) ViewGroup cameraControlsLayout;
    @BindView(R.id.camera_timer) ImageView cameraTimer;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindViews({R.id.shot_button, R.id.camera_mode_button, R.id.camera_flash}) List<View> cameraControls;
    private boolean isLoading = false;
    
    private Context context = this;
    private CameraPresenter cameraPresenter;
    private PhotoTransferManager photoTransferManager;

    private AnimatorSet flipIn;
    private AnimatorSet flipOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        cameraPresenter = new CameraPresenter(this, surfaceView, cameraModeButton, cameraFlash,
                cameraTimer, time, overlayBlack, focusImage, flash, cameraControlsLayout, this, this);

        photoTransferManager = PhotoTransferManager.getInstance();

        flipIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.card_flip_in);
        flipOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.card_flip_out);
    }

    @Override
    public void onStartLoading() {
        isLoading = true;
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopLoading() {
        isLoading = false;
        if (progressBar != null) progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setControlsEnabled(boolean enabled) {
        ButterKnife.apply(cameraControls, new ButterKnife.Action<View>() {
            @Override
            public void apply(@NonNull View view, int index) {
                view.setEnabled(enabled);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        cameraPresenter.start();
    }


    @Override
    protected void onResume() {
        super.onResume();
        cameraPresenter.resume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        cameraFlash.setRotation(RotationDetector.getRangeMiddle(newConfig.orientation));
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraPresenter.pause();
    }

    @Override
    protected void onStop() {
        cameraPresenter.stop();
        super.onStop();
    }

    private void onFocusStarted(float x, float y) {
        focusImage.setVisibility(View.VISIBLE);
        focusImage.setX(x - focusImage.getMeasuredWidth() / 2);
        focusImage.setY(y - focusImage.getMeasuredHeight() / 2);
        focusImage.setScaleX(1);
        focusImage.setScaleY(1);
        focusImage.setAlpha(1f);

        AnimatorSet focus = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.focus);
        focus.setTarget(focusImage);
        focus.start();
    }

    private void onFocusEnded() {
        focusImage.animate().alpha(0f).setDuration(100).setListener(new AbstractSimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                focusImage.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void savePhotoAndContinue(Bitmap bitmap) {
        onStartLoading();

        new AsyncTask<Bitmap, Void, Void>() {
            @Override
            protected Void doInBackground(Bitmap... params) {
                photoTransferManager.saveBitmap(params[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                boolean square = bitmap.getHeight() == bitmap.getWidth();
                onStopLoading();
                onPhotoSaved();
            }
        }.execute(bitmap);
    }

    private void onPhotoSaved() {
        onBackPressed();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Clicks
    ///////////////////////////////////////////////////////////////////////////

    @OnTouch(R.id.surfaceView)
    public boolean onClick3(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                if (!isLoading && cameraPresenter.getCameraIndex() == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    onFocusStarted(event.getX(), event.getY() + ((ConstraintLayout.LayoutParams) (surfaceView.getLayoutParams())).topMargin);
                    cameraPresenter.focus(event.getX(), event.getY(), () -> {
                        setControlsEnabled(true);
                        onFocusEnded();
                    });
                }

            }
        return true;
    }

    @OnClick(R.id.camera_mode_button)
    public void onClick() {
        if (cameraPresenter.getCameraIndex() == Camera.CameraInfo.CAMERA_FACING_BACK) {
            flipIn.setTarget(cameraModeButton);
            flipIn.start();
        } else {
            flipOut.setTarget(cameraModeButton);
            flipOut.start();
        }
        cameraPresenter.changeCamera(cameraPresenter.getCameraIndex() == Camera.CameraInfo.CAMERA_FACING_BACK ?
                Camera.CameraInfo.CAMERA_FACING_FRONT : Camera.CameraInfo.CAMERA_FACING_BACK);
    }


    @OnClick(R.id.camera_flash)
    public void onClick2() {
        cameraPresenter.changeFlashlightStatus(!cameraPresenter.isFlashLightEnabled());
    }

    @OnClick(R.id.camera_timer)
    public void onClick4() {
        if (!isLoading) {
            cameraPresenter.createTimer();
        }
    }

    @OnClick(R.id.shot_button)
    public void onClick1() {
        if (!cameraPresenter.isTimerEnabled())
            cameraPresenter.makePhoto(this::savePhotoAndContinue);
        else {
            cameraPresenter.makePhotoWithTimer(this::savePhotoAndContinue);
        }
    }
}
