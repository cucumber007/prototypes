package com.polyana.cucumber007.copypaste.demo;

import android.os.Bundle;

import com.polyana.cucumber007.copypaste.R;
import com.polyana.cucumber007.copypaste.activities.AbstractPopupActivity;

public class FloatingActivity extends AbstractPopupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_floating);
    }


}
