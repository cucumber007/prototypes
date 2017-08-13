package com.cucumber007.prototypes.activities.fragments_sandbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucumber007.prototypes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestParentFragment extends Fragment {

    @BindView(R.id.list_pager) ViewPager listPager;

    private Fragment fragment1 = new TestChildFragment();
    private Fragment fragment2 = new TestChildFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_parent, container, false);
        ButterKnife.bind(this, view);

        listPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0: return fragment1;
                    case 1: return fragment2;
                } return null;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
