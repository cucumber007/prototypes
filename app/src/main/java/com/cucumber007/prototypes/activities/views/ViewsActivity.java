package com.cucumber007.prototypes.activities.views;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ImageView;

import com.cucumber007.prototypes.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ViewsActivity extends Activity {

    @Bind(R.id.imageView) ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_views);
        ButterKnife.bind(this);

        imageView.getLayoutParams().height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        imageView.getLayoutParams().width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
    }
}
