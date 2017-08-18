package com.cucumber007.prototypes.sandbox.tabs.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucumber007.prototypes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends AbstractTabFragment {

    private static int numTabs = 0;
    private String title;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        numTabs++;
        title = "Tab "+numTabs;
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    String getTitle() {
        return title;
    }
}
