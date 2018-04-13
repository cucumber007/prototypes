package com.cucumber007.prototypes.cases;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.menu.SandboxItemFragment;

public class CaseFragment extends SandboxItemFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getSandboxItem().run();
        return inflater.inflate(R.layout.fragment_case, container, false);
    }

}
