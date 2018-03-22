package com.cucumber007.prototypes.sandbox.livedata;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.cucumber007.prototypes.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LivedataActivity extends AppCompatActivity {

    @BindView(R.id.textView) TextView textView;

    private NameViewModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livedata);
        ButterKnife.bind(this);

        mModel = ViewModelProviders.of(this).get(NameViewModel.class);

        final Observer<String> nameObserver = newName -> {
            textView.setText(newName);
        };

        mModel.getCurrentName().observe(this, nameObserver);
    }

    @OnClick(R.id.textView)
    public void onClick() {
        String anotherName = "John Doe "+Math.random();
        mModel.getCurrentName().setValue(anotherName);
    }
}
