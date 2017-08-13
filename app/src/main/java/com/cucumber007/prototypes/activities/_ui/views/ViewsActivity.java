package com.cucumber007.prototypes.activities._ui.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.graphics.Palette;
import android.util.TypedValue;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucumber007.prototypes.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewsActivity extends Activity {

    @BindView(R.id.imageView) ImageView imageView;
    @BindView(R.id.imageView13) ImageView imageView13;
    @BindView(R.id.textView7) TextView textView7;

    ViewTreeObserver.OnGlobalLayoutListener listener;
    @BindView(R.id.frame) RelativeLayout frame;
    @BindView(R.id.imageView14) ImageView imageView14;
    @BindView(R.id.imageView15) ImageView imageView15;
    @BindView(R.id.compat) ImageView compat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_views);
        ButterKnife.bind(this);

        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        imageView.getLayoutParams().height = size;
        imageView.getLayoutParams().width = size;

        listener = () -> {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) textView7.getLayoutParams();
            float max = 0;
            for (int i = 0; i < textView7.getLineCount(); i++) {
                float line = textView7.getLayout().getLineMax(i);
                if (line > max) max = line;
            }
            lp.width = (int) Math.ceil(max);
            lp.setMargins((int) ((frame.getWidth() - lp.width) / 2.), 0, 0, lp.bottomMargin);
            lp.addRule(RelativeLayout.ALIGN_RIGHT, RelativeLayout.TRUE);
            textView7.setLayoutParams(lp);
            frame.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
            //frame.requestLayout();
            //frame.invalidate();
        };
        frame.getViewTreeObserver().addOnGlobalLayoutListener(listener);

        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), BitmapFactory.decodeResource(getResources(),
                R.drawable.tutorial_1));
        drawable.setCornerRadius(100);
        imageView13.setImageDrawable(drawable);

        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tutorial_1);
        if (myBitmap != null && !myBitmap.isRecycled()) {
            Palette.from(myBitmap).generate(palette -> {
                int def = 0x000000;
                int vibrant = palette.getVibrantColor(def);
                int vibrantLight = palette.getLightVibrantColor(def);
                int vibrantDark = palette.getDarkVibrantColor(def);
                int muted = palette.getMutedColor(def);
                int mutedLight = palette.getLightMutedColor(def);
                int mutedDark = palette.getDarkMutedColor(def);
                imageView.setImageDrawable(new ColorDrawable(vibrant));
                imageView14.setImageDrawable(new ColorDrawable(vibrantDark));
                imageView15.setImageDrawable(new ColorDrawable(muted));
            });
        }

        Drawable normalDrawable = getResources().getDrawable(R.mipmap.ic_launcher);
        Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);
        DrawableCompat.setTint(wrapDrawable, getResources().getColor(R.color.blue));
        compat.setImageDrawable(wrapDrawable);
    }

    @OnClick(R.id.button8)
    void click() {
        startActivity(new Intent(this, StylesActivity.class));
    }

    @OnClick(R.id.button13)
    void click1() {
        startActivity(new Intent(this, GridViewActivity.class));
    }
}
