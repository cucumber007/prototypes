package com.cucumber007.prototypes.activities.fragments_sandbox;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.tseh20.giver.R;
import com.tseh20.giver.activities.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;


public abstract class AbstractTabsFragment extends AbstractTitledFragment {

    //todo to prototypes
    @Bind(R.id.list_pager) ViewPager listPager;

    @Bind(R.id.tabs) PagerSlidingTabStrip tabs;
    @Bind(R.id.expandableLayout) ExpandableRelativeLayout expandableLayout;

    private boolean tabsLoading = false;

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
            public int getCount() {
                return getTabCount();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (getItem(position) instanceof AbstractTitledFragment)
                    return ((AbstractTitledFragment) getItem(position)).getTitle();
                else return "";
            }
        });
        tabs.setViewPager(listPager);

        return view;
    }

    public PagerSlidingTabStrip getTabs() {
        return tabs;
    }


    protected ViewPager getPager() {
        return listPager;
    }

    public ExpandableRelativeLayout getExpandableLayout() {
        return expandableLayout;
    }

    abstract int getTabCount();

    abstract Fragment getFragmentOnPosition(int position);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void setTabsLoading(boolean tabsLoading) {
        ((MainActivity) getActivity()).setPendingFragmentAction(false);
    }
}
