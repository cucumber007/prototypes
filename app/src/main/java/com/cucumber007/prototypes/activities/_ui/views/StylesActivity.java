package com.cucumber007.prototypes.activities._ui.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.cucumber007.prototypes.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StylesActivity extends Activity {

    @Bind(R.id.textView3) TextView textView3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_styles);
        ButterKnife.bind(this);
    }

}
