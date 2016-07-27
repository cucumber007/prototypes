package com.cucumber007.prototypes.activities._ui.viewpager;

public abstract class OpacityViewPagerScrollPositionListener extends ViewPagerScrollPositionListener {

    @Override
    public void onScroll(int current, int next, float percent) {
        int pageToDisplay;
        float opacity;
        //right
        if (next > current) {
            if (percent > 0.5) {
                pageToDisplay = next;
                opacity = (percent - 0.5f) / 0.5f;
            } else {
                pageToDisplay = current;
                opacity = (0.5f - percent) / 0.5f;
            }
            //left
        } else {
            if (percent > 0.5) {
                pageToDisplay = current;
                opacity = (percent - 0.5f) / 0.5f;
            } else {
                pageToDisplay = next;
                opacity = (0.5f - percent) / 0.5f;
            }
        }
        onPageScroll(pageToDisplay, opacity);
    }
    public abstract void onPageScroll(int page, float opacity);
}

