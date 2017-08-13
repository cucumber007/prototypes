package com.cucumber007.prototypes.activities._ui.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.cucumber007.prototypes.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public abstract class AbstractTabsFragment extends AbstractTitledFragment {


    @BindView(R.id.list_pager) ViewPager listPager;
    @BindView(R.id.tabs) PagerSlidingTabStrip tabs;
    //todo to prototypes

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabs, container, false);
        Context context = getContext();
        ButterKnife.bind(this, view);

        listPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return getFragmentOnPosition(position);
            }

            @Override
            public int getCount() { return getTabCount(); }

            @Override
            public CharSequence getPageTitle(int position) {
                if(getItem(position) instanceof AbstractTitledFragment) return ((AbstractTitledFragment) getItem(position)).getTitle();
                else return "";
            }
        });
        tabs.setViewPager(listPager);

        return view;
    }


    abstract int getTabCount();

    abstract Fragment getFragmentOnPosition(int position);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
