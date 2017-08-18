package com.cucumber007.prototypes.sandbox.tabs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.cucumber007.prototypes.R;
import com.roughike.bottombar.BottomBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabsActivity extends AppCompatActivity {

    @BindView(R.id.bottomBar) BottomBar bottomBar;
    @BindView(R.id.tab1_layout) FrameLayout tab1Layout;
    @BindView(R.id.tab2_layout) FrameLayout tab2Layout;
    @BindView(R.id.tab3_layout) FrameLayout tab3Layout;

    private TabsPresenter tabsPresenter;

    public static final int TAB_1 = 0;
    public static final int TAB_2 = 1;
    public static final int TAB_3 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs2);
        ButterKnife.bind(this);

        tabsPresenter = new TabsPresenter();
        tabsPresenter.addLayout(TAB_1, tab1Layout);
        tabsPresenter.addLayout(TAB_2, tab2Layout);
        tabsPresenter.addLayout(TAB_3, tab3Layout);

        bottomBar.setOnTabSelectListener(tabId -> {
            switch (tabId) {
                case R.id.tab_1:
                    tabsPresenter.switchLayout(TAB_1);
                    break;
                case R.id.tab_2:
                    tabsPresenter.switchLayout(TAB_2);
                    break;
                case R.id.tab_3:
                    tabsPresenter.switchLayout(TAB_3);
                    break;
            }
        });
    }
}
