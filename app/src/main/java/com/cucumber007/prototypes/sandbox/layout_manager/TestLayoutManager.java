package com.cucumber007.prototypes.sandbox.layout_manager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class TestLayoutManager extends RecyclerView.LayoutManager {

    public static final float SCALE_THRESHOLD_PERCENT = 0.33f;

    private Context context;

    public TestLayoutManager(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        detachAndScrapAttachedViews(recycler);

        int lastChildYPosition = 0;
        for (int position = 0; position < getItemCount(); position++) {
            View view = recycler.getViewForPosition(position);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int height = getDecoratedMeasuredHeight(view);
            layoutDecorated(view, 0, lastChildYPosition, getDecoratedMeasuredWidth(view), lastChildYPosition + height);
            lastChildYPosition += height;
        }

    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }


    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int delta = scrollVerticallyInternal(dy);
        offsetChildrenVertical(-delta);
        offsetChildrenHorizontal(delta/7);

        updateViewScale();
        return delta;
    }

    private int scrollVerticallyInternal(int dy) {
        int childCount = getChildCount();
        int itemCount = getItemCount();
        if (childCount == 0){
            return 0;
        }

        final View topView = getChildAt(0);
        final View bottomView = getChildAt(childCount - 1);

        //Случай, когда все вьюшки поместились на экране
        int viewSpan = getDecoratedBottom(bottomView) - getDecoratedTop(topView);
        if (viewSpan <= getHeight()) {
            return 0;
        }

        int delta = 0;
        //если контент уезжает вниз
        if (dy < 0){
            View firstView = getChildAt(0);
            int firstViewAdapterPos = getPosition(firstView);
            if (firstViewAdapterPos > 0){ //если верхняя вюшка не самая первая в адаптере
                delta = dy;
            } else { //если верхняя вьюшка самая первая в адаптере и выше вьюшек больше быть не может
                int viewTop = getDecoratedTop(firstView);
                delta = Math.max(viewTop, dy);
            }
        } else if (dy > 0){ //если контент уезжает вверх
            View lastView = getChildAt(childCount - 1);
            int lastViewAdapterPos = getPosition(lastView);
            if (lastViewAdapterPos < itemCount - 1){ //если нижняя вюшка не самая последняя в адаптере
                delta = dy;
            } else { //если нижняя вьюшка самая последняя в адаптере и ниже вьюшек больше быть не может
                int viewBottom = getDecoratedBottom(lastView);
                int parentBottom = getHeight();
                delta = Math.min(viewBottom - parentBottom, dy);
            }
        }
        return delta;
    }

    private void updateViewScale() {
        int childCount = getChildCount();
        int height = getHeight();
        int thresholdPx = (int) (height * SCALE_THRESHOLD_PERCENT);

        for (int i = 0; i < childCount; i++) {
            float scale = 1f;
            View view = getChildAt(i);
            int viewTop = getDecoratedTop(view);
            if (viewTop >= thresholdPx){
                int delta = viewTop - thresholdPx;
                scale = (height - delta) / (float)height;
                scale = Math.max(scale, 0);
            }
            view.setPivotX(view.getHeight()/2);
            view.setPivotY(view.getHeight() / -2);
            view.setScaleX(scale);
            view.setScaleY(scale);
        }
    }
}
