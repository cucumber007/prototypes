package com.cucumber007.prototypes.activities._ui.coordinator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.cucumber007.prototypes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoordinatorLayoutActivity extends AppCompatActivity {


    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        ButterKnife.bind(this);

        toolbar.setTitle("Coordinator Layout");
    }
}
