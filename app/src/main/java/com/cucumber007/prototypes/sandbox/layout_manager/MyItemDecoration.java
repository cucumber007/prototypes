package com.cucumber007.prototypes.sandbox.layout_manager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cucumber007.prototypes.R;

public class MyItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    public MyItemDecoration(Context context) {
        mDivider = context.getResources().getDrawable(R.color.white);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);

        view.setBackgroundColor(position % 2 == 0 ? view.getContext().getResources().getColor(R.color.red) : view.getContext().getResources().getColor(R.color.blue));

        if (position % 2 == 0) {
            return;
        }

        int value = 100;
        outRect.left = value;
        outRect.right = value;
        outRect.top = value;
        outRect.bottom = value;

        //here you can set additionsl child padding
    }


    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int dividerLeft = parent.getPaddingLeft();
        int dividerRight = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int dividerTop = child.getBottom() + params.bottomMargin;
            //int dividerBottom = dividerTop + mDivider.getIntrinsicHeight();
            int dividerBottom = dividerTop + 100;

            mDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
            mDivider.draw(canvas);
        }

        //here you can draw some stuff under the child layout
    }

}
