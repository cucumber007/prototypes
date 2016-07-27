package com.cucumber007.prototypes.activities._ui.viewpager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cucumber007.prototypes.R;

import java.util.ArrayList;

public  class TutorialPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private final Context context;
    private final ArrayList<Drawable> images = new ArrayList<>();


    public TutorialPagerAdapter(Context context) {
        super();
        this.context = context;
        images.add(this.context.getResources().getDrawable(R.drawable.tutorial_1));
        images.add(this.context.getResources().getDrawable(R.drawable.tutorial_1));
        images.add(this.context.getResources().getDrawable(R.drawable.tutorial_1));
        images.add(this.context.getResources().getDrawable(R.drawable.tutorial_1));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View layout = ((LayoutInflater)container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.page_tutorial, null);
        ((ImageView)layout.findViewById(R.id.image)).setImageDrawable(images.get(position));

        container.addView(layout);
        return layout;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
