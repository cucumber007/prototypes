package com.cucumber007.prototypes.activities.giver;

import android.support.v4.view.ViewPager;

public abstract class ViewPagerScrollPositionListener implements ViewPager.OnPageChangeListener {

    private int currentPage = 0;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int next = position == currentPage ? currentPage+1 : currentPage-1;
        onScroll(currentPage, next, positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        currentPage = position;
        //LogUtil.logDebug(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public abstract void onScroll(int current, int next, float percent);
}

