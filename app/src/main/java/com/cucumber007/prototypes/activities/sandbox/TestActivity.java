package com.cucumber007.prototypes.activities.sandbox;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.cucumber007.prototypes.R;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Log.d("cutag", "new activity created");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("cutag", "new activity resumed");
    }
}
