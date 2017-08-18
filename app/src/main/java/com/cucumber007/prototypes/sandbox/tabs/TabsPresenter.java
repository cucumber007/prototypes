package com.cucumber007.prototypes.sandbox.tabs;


import android.animation.Animator;
import android.util.SparseArray;
import android.view.View;

import com.cucumber007.prototypes.reusables.listeners.AbstractSimpleAnimatorListener;


public class TabsPresenter {

    private SparseArray<TabContainer> tabs = new SparseArray<>();
    private int currentLayoutId = -1;
    private View visibleLayout;
    private boolean isAnimating = false;
    public static final int FADE_DURATION = 150;

    public SparseArray<TabContainer> getTabs() {
        return tabs;
    }

    public void switchLayout(int id) {
        if (currentLayoutId != id) {
            currentLayoutId = id;
            switchLayout(getLayoutByTabId(id));
        }
    }

    private void switchLayout(View layout) {
        if (!isAnimating) {
            if (visibleLayout != null) {
                /*if (bottomBar.getCurrentTabPosition() != currentLayoutId && currentLayoutId != SEARCH) {
                    bottomBar.selectTabAtPosition(currentLayoutId);
                }*/
                if (!visibleLayout.equals(layout)) {
                    onAnimationStarted();
                    visibleLayout.animate().alpha(0).setDuration(FADE_DURATION).setListener(new AbstractSimpleAnimatorListener() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            visibleLayout.setVisibility(View.GONE);
                            visibleLayout = layout;
                        }
                    });
                    layout.setVisibility(View.VISIBLE);
                    layout.setAlpha(0);
                    layout.animate().alpha(1).setDuration(FADE_DURATION).setListener(new AbstractSimpleAnimatorListener() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            onAnimationEnded();
                        }
                    });
                }
            } else {
                onAnimationStarted();
                visibleLayout = layout;
                layout.setVisibility(View.VISIBLE);
                layout.setAlpha(0);
                layout.animate().alpha(1).setDuration(FADE_DURATION).setListener(new AbstractSimpleAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        onAnimationEnded();
                    }
                });
            }
        }
    }

    private void onAnimationStarted() {
        isAnimating = true;
    }

    private void onAnimationEnded() {
        isAnimating = false;
    }

    public void addLayout(int id, View layout) {
        tabs.append(id, new TabContainer(layout));
    }

    private View getLayoutByTabId(int id) {
        return tabs.get(id).getLayout();
    }

    protected class TabContainer {

        private View layout;

        public TabContainer(View layout) {
            this.layout = layout;
        }

        public View getLayout() {
            return layout;
        }
    }

    public int getCurrentLayoutId() {
        return currentLayoutId;
    }
}
