package com.cucumber007.prototypes.activities._ui.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucumber007.prototypes.R;


public class HelpFragment extends AbstractTitledFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

    @Override
    public String getTitle() {
        return "Test fragment";
    }
}
