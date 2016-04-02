package com.cucumber007.prototypes.activities.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ImageView;

import com.cucumber007.prototypes.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewsActivity extends Activity {

    @Bind(R.id.imageView) ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_views);
        ButterKnife.bind(this);

        int size = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        imageView.getLayoutParams().height = size;
        imageView.getLayoutParams().width = size;
    }

    @OnClick(R.id.button8)
    void click() {
        startActivity(new Intent(this, StylesActivity.class));
    }
}
