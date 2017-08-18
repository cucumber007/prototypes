package com.cucumber007.prototypes.sandbox.tabs;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucumber007.prototypes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawerActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tab1_layout) FrameLayout tab1Layout;
    @BindView(R.id.tab2_layout) FrameLayout tab2Layout;
    @BindView(R.id.tab3_layout) FrameLayout tab3Layout;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.drawer_close) ImageView drawerClose;
    @BindView(R.id.drawer_tab1) TextView drawerTab1;
    @BindView(R.id.drawer_tab2) TextView drawerTab2;
    @BindView(R.id.drawer_tab3) TextView drawerTab3;

    private DrawerPresenter<TextView> drawerPresenter;
    private TabsPresenter tabsPresenter;

    public static final int TAB_1 = 0;
    public static final int TAB_2 = 1;
    public static final int TAB_3 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        tabsPresenter = new TabsPresenter();
        tabsPresenter.addLayout(TAB_1, tab1Layout);
        tabsPresenter.addLayout(TAB_2, tab2Layout);
        tabsPresenter.addLayout(TAB_3, tab3Layout);

        drawerPresenter = new DrawerPresenter<>((selectedId, selectedItem, lastSelectedId, lastSelectedItem) -> {
            tabsPresenter.switchLayout(selectedId);
            if (lastSelectedItem != null) lastSelectedItem.setTypeface(null, Typeface.NORMAL);
            selectedItem.setTypeface(null, Typeface.BOLD);
            drawer.closeDrawer(Gravity.LEFT);
            getSupportActionBar().setTitle(selectedItem.getText());
        });
        drawerPresenter.addItem(TAB_1, drawerTab1);
        drawerPresenter.addItem(TAB_2, drawerTab2);
        drawerPresenter.addItem(TAB_3, drawerTab3);
        drawerPresenter.selectItem(TAB_1);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
}
