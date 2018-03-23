package com.cucumber007.prototypes.cases.state_and_callbacks.solutions.livedata;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.cucumber007.prototypes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LivedataRotationActivity extends AppCompatActivity {

    @BindView(R.id.imageView) ImageView imageView;

    private BitmapViewModel bitmapViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callbacks);
        ButterKnife.bind(this);

        bitmapViewModel = ViewModelProviders.of(this).get(BitmapViewModel.class);
        bitmapViewModel.update();

        bitmapViewModel.getBitmap().observe(this, bitmap -> {
            imageView.setImageBitmap(bitmap);
        });
    }

}
