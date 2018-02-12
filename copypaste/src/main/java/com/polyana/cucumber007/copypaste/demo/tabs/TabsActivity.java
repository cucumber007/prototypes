package com.polyana.cucumber007.copypaste.demo.tabs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.cucumber007.reusables.tabs.TabsPresenter;
import com.polyana.cucumber007.copypaste.R;
import com.polyana.cucumber007.copypaste.sample.SampleFragment;
import com.roughike.bottombar.BottomBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabsActivity extends AppCompatActivity {

    @BindView(R.id.bottomBar) BottomBar bottomBar;

    private TabsPresenter tabsPresenter;

    private SampleFragment fragment1 = SampleFragment.newInstance();
    private SampleFragment fragment2 = SampleFragment.newInstance();
    private SampleFragment fragment3 = SampleFragment.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        ButterKnife.bind(this);

        tabsPresenter = new TabsPresenter(this, R.id.root);
        tabsPresenter.addTab(R.id.tab_1, fragment1);
        tabsPresenter.addTab(R.id.tab_2, fragment2);
        tabsPresenter.addTab(R.id.tab_3, fragment3);

        bottomBar.setOnTabSelectListener(tabId -> tabsPresenter.onViewClicked(tabId));
    }
}
