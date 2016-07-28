package com.cucumber007.prototypes.activities._ui.viewpager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cucumber007.prototypes.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ViewPagerActivity extends AppCompatActivity {

    @Bind(R.id.tutorial_pager) ViewPager tutorialPager;
    @Bind(R.id.title) TextView title;

    //todo endless pager
    //todo pager cache > 3
    //todo fragment pager
    //todo pagertabstrip

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        ButterKnife.bind(this);

        TutorialPagerAdapter adapter = new TutorialPagerAdapter(this);
        tutorialPager.setAdapter(adapter);

        tutorialPager.addOnPageChangeListener(new OpacityViewPagerScrollPositionListener() {

            @Override
            public void onPageScroll(int page, float opacity) {
                title.setText(""+ page);
                title.setAlpha(opacity);
            }
        });

    }

}
