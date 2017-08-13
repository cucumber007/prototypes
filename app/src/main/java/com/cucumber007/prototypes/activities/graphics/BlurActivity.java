package com.cucumber007.prototypes.activities.graphics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cucumber007.prototypes.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BlurActivity extends AppCompatActivity {

    @BindView(R.id.imageView10) ImageView imageView10;
    @BindView(R.id.imageView11) ImageView imageView11;
    @BindView(R.id.root) RelativeLayout root;
    @BindView(R.id.cover) FrameLayout cover;

    long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.imageView10)
    public void onClick() {
        time = System.currentTimeMillis();
        BlurBehind.getInstance().execute(root, () -> {
            BlurBehind.getInstance().setBackground(cover);
            Log.d("cutag", ""+(System.currentTimeMillis()-time));
            if(cover.getVisibility() != View.VISIBLE) cover.setVisibility(View.VISIBLE);
            startActivity(new Intent(this, AfterBlurActivity.class));
        });
    }

    @Override
    protected void onResume() {
        if(cover.getVisibility() == View.VISIBLE) cover.setVisibility(View.GONE);
        super.onResume();
    }
}
