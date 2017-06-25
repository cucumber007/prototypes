package com.cucumber007.prototypes.activities.giver;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;

public abstract class AbstractSwitchableFragment extends Fragment{
    public abstract String getTitle();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (getView() != null) {
            getView().setOnTouchListener((View v, MotionEvent event) -> true);
        }
        super.onActivityCreated(savedInstanceState);
    }

    public abstract void onShow();
}
