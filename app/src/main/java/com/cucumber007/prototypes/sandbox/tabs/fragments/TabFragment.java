package com.cucumber007.prototypes.sandbox.tabs.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cucumber007.prototypes.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends AbstractTabFragment {

    private static int numTabs = 0;
    @BindView(R.id.tv_content) TextView tvContent;
    Unbinder unbinder;
    private String title;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        unbinder = ButterKnife.bind(this, view);

        numTabs++;
        title = "Tab " + numTabs;
        tvContent.setText(title);

        return view;
    }

    @Override
    String getTitle() {
        return title;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
