package com.cucumber007.prototypes.activities.orientation;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cucumber007.prototypes.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrientationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.orientation_button)
    public void onClick() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

    }

    @OnClick(R.id.button12)
    public void onClick1() {
        Log.d("cutag", ""+getRequestedOrientation());
        setContentView(R.layout.activity_orientation);
        ButterKnife.bind(this);
    }
}
