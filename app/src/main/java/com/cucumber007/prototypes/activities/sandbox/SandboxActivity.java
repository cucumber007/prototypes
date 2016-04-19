package com.cucumber007.prototypes.activities.sandbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cucumber007.prototypes.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SandboxActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button10)
    public void onClick() {
        Log.d("cutag", "click");
        startActivity(new Intent(this, TestActivity.class));
        finish();
        Log.d("cutag", "afterlife");
        int a = 0;
        while (a < 20000) {
            Log.d("cutag", "azaza");
            a++;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("cutag", "parent onPause");
    }
}
