package com.cucumber007.prototypes.activities.fragments_sandbox;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cucumber007.prototypes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestChildFragment extends Fragment {


    @BindView(R.id.text) TextView text;

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_child, container, false);
        ButterKnife.bind(this, view);
        context = getActivity();

        text.setText(""+Math.random());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
