package com.cucumber007.prototypes.sandbox.tabs.fragments;

import android.support.v4.app.Fragment;

public abstract class AbstractTabFragment extends Fragment {

    void onShow() {};
    String getTitle() {
        return getClass().toString();
    };

}
