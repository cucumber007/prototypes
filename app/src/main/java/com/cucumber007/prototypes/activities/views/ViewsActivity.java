package com.cucumber007.prototypes.activities.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucumber007.prototypes.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewsActivity extends Activity {

    @Bind(R.id.imageView) ImageView imageView;
    @Bind(R.id.textView7) TextView textView7;

    ViewTreeObserver.OnGlobalLayoutListener listener;
    @Bind(R.id.frame) RelativeLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_views);
        ButterKnife.bind(this);

        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        imageView.getLayoutParams().height = size;
        imageView.getLayoutParams().width = size;

        listener = () -> {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)textView7.getLayoutParams();
            lp.width = (int) textView7.getLayout().getLineMax(1);
            lp.setMargins((int)((frame.getWidth()-lp.width)/2.),0,0,lp.bottomMargin);
            lp.addRule(RelativeLayout.ALIGN_RIGHT, RelativeLayout.TRUE);
            textView7.setLayoutParams(lp);
            frame.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
            frame.requestLayout();
            frame.invalidate();
            Log.d("cutag", "ok");
        };
        frame.getViewTreeObserver().addOnGlobalLayoutListener(listener);

    }

    @OnClick(R.id.button8)
    void click() {
        startActivity(new Intent(this, StylesActivity.class));
    }
}
