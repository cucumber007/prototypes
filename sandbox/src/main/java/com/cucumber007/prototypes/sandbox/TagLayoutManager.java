package com.cucumber007.prototypes.sandbox;

import android.support.v7.widget.RecyclerView;
import android.view.View;

//todo from homy
public class TagLayoutManager extends RecyclerView.LayoutManager {

    public TagLayoutManager() {
        setAutoMeasureEnabled(true);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }



    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);

        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();
        if (itemCount == 0) return;

        int recyclerWidth = getWidth();
        int rowTop = 0;
        int remainingWidth = recyclerWidth;

        for (int i = 0; i < itemCount; i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            //todo handle margins
            //todo handle different item heights
            measureChildWithMargins(view, 0, 0);
            if(remainingWidth >= view.getMeasuredWidth()) {
                layoutItem(view, recyclerWidth, remainingWidth, rowTop, view.getMeasuredWidth(), view.getMeasuredHeight());
                remainingWidth -= view.getMeasuredWidth();
            } else {
                rowTop += view.getMeasuredHeight();
                remainingWidth = recyclerWidth;
                layoutItem(view, recyclerWidth, remainingWidth, rowTop, view.getMeasuredWidth(), view.getMeasuredHeight());
                remainingWidth -= view.getMeasuredWidth();
            }

        }
    }

    private void layoutItem(View view, int recyclerWidth, int widthLeft, int y, int width, int height) {
        int x = recyclerWidth - widthLeft;
        //LogUtil.logDebug("layout", recyclerWidth, widthLeft, x, y, x+width, y+height);
        layoutDecorated(view, x, y, x+width, y+height);
    }

}
