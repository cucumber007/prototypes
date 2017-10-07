package com.cucumber007.prototypes.sandbox.fragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.cucumber007.prototypes.R;
import com.cucumber007.reusables.logging.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HiddenFragmentActivity extends AppCompatActivity {

    @BindView(R.id.layout) FrameLayout layout;

    private HiddenFragment hiddenFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_fragment);
        ButterKnife.bind(this);

        LogUtil.logDebug("HiddenActivity");

        hiddenFragment = (HiddenFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

    private void hideFragment() {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_out, R.anim.fade_out)
                .hide(hiddenFragment).commit();
    }

    private void showFragment() {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_out, R.anim.fade_out)
                .show(hiddenFragment).commit();
    }

    @OnClick(R.id.b_state)
    public void onClick() {
        if(hiddenFragment.isHidden()) showFragment();
        else hideFragment();
    }
}
