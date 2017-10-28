package com.polyana.cucumber007.copypaste.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.polyana.cucumber007.copypaste.R;

import butterknife.ButterKnife;

public class AbstractPopupActivity extends Activity {

    private ViewGroup root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abstract_popup);
        ButterKnife.bind(this);

        root = (ViewGroup) findViewById(R.id.root);
    }

    protected void setLayout(@LayoutRes int layout) {
        LayoutInflater.from(this).inflate(layout, root);
    }
}
