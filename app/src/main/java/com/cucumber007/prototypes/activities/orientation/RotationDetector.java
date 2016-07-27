package com.cucumber007.prototypes.activities.orientation;

import android.content.Context;
import android.hardware.SensorManager;
import android.view.OrientationEventListener;

public class RotationDetector extends OrientationEventListener {

    public RotationDetector(Context context) {
        super(context, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onOrientationChanged(int orientation) {
        //Log.d("cutag", ""+orientation);
    }

    @Override
    public void enable() {
        if (canDetectOrientation()) super.enable();
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
