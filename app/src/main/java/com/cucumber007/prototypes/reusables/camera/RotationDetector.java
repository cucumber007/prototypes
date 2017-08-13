package com.cucumber007.prototypes.reusables.camera;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.view.OrientationEventListener;

public class RotationDetector extends OrientationEventListener {

    private final OnTimeToChangeOrientationListener listener;
    private int currentOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    private int deltaDegrees = 80;

    public RotationDetector(Context context, OnTimeToChangeOrientationListener listener) {
        super(context, SensorManager.SENSOR_DELAY_NORMAL);
        this.listener = listener;
    }

    @Override
    public void onOrientationChanged(int degrees) {
        if (degrees > 0) {
            int middle = getRangeMiddle(currentOrientation);
            int left = middle - deltaDegrees;
            if (left < 0) left = 360 + left;
            int right = middle + deltaDegrees;
            if (!checkInRange(degrees, left, right)) {
                currentOrientation = getOrientationIdByDegree(degrees);
                listener.change(currentOrientation);
            }
            //Log.d("cutag", degrees+ " | " + left + " "+ right + " | "+checkInRange(degrees, left, right)+" " + currentOrientation);
        }
    }

    @Override
    public void enable() {
        if (canDetectOrientation()) super.enable();
    }

    private boolean checkInRange(int check, int start, int end) {
        if (start > end) {
            return (check >= start && check <= 360) || (check < end);
        } else {
            return (check >= start && check <= end);
        }
    }

    private int getOrientationIdByDegree(int degree) {
        if (degree < 0) return -1;
        else if (degree < 45) return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        else if (degree < 135) return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        else if (degree < 225) return ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
        else if (degree < 315) return ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
        else return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    public static int getRangeMiddle(int orientationId) {
        switch (orientationId) {
            case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                return 0;
            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
                return 90;
            case ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT:
                return 180;
            case ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE:
                return 270;
            default:
                return -1;
        }
    }

    public static int getRangeMiddleMirror(int orientationId) {
        switch (orientationId) {
            case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                return 0;
            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
                return 270;
            case ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT:
                return 180;
            case ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE:
                return 90;
            default:
                return -1;
        }
    }

    public interface OnTimeToChangeOrientationListener {
        void change(int orientation);
    }

    public int getCurrentOrientation() {
        return currentOrientation;
    }

    //todo
    /*if (myOrientationEventListener.canDetectOrientation()){
            Toast.makeText(this, "Can DetectOrientation", Toast.LENGTH_LONG).show();
            myOrientationEventListener.enable();
        }
        else{
            Toast.makeText(this, "Can't DetectOrientation", Toast.LENGTH_LONG).show();
            finish();
        } */
}
