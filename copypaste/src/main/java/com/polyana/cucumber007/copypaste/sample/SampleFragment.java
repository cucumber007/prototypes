package com.polyana.cucumber007.copypaste.sample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cucumber007.reusables.tabs.AbstractTabFragment;
import com.polyana.cucumber007.copypaste.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SampleFragment extends AbstractTabFragment {

    private static int quantity = 0;
    private String title;

    @BindView(R.id.text) TextView text;
    Unbinder unbinder;

    public static SampleFragment newInstance() {
        return new SampleFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sample, container, false);
        unbinder = ButterKnife.bind(this, view);

        text.setText(getTitle());
        quantity++;
        return view;
    }

    @Override
    public String getTitle() {
        if (title == null) {
            title = "Sample fragment " + quantity;
        }
        return title;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
