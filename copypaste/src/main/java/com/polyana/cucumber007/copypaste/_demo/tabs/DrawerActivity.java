package com.polyana.cucumber007.copypaste._demo.tabs;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.TextView;

import com.cucumber007.reusables.tabs.DrawerPresenter;
import com.cucumber007.reusables.tabs.TabsPresenter;
import com.polyana.cucumber007.copypaste.R;
import com.polyana.cucumber007.copypaste.sample.SampleFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawerActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.drawer_tab1) TextView drawerTab1;
    @BindView(R.id.drawer_tab2) TextView drawerTab2;
    @BindView(R.id.drawer_tab3) TextView drawerTab3;

    private SampleFragment fragment1 = SampleFragment.newInstance();
    private SampleFragment fragment2 = SampleFragment.newInstance();
    private SampleFragment fragment3 = SampleFragment.newInstance();

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

        tabsPresenter = new TabsPresenter(this, R.id.root);
        tabsPresenter.addTab(drawerTab1.getId(), fragment1);
        tabsPresenter.addTab(drawerTab2.getId(), fragment2);
        tabsPresenter.addTab(drawerTab3.getId(), fragment3);

        drawerPresenter = new DrawerPresenter<>((selectedItem, lastSelectedItem) -> {
            tabsPresenter.onViewClicked(selectedItem.getId());
            if (lastSelectedItem != null) lastSelectedItem.setTypeface(null, Typeface.NORMAL);
            selectedItem.setTypeface(null, Typeface.BOLD);
            drawer.closeDrawer(Gravity.LEFT);
            getSupportActionBar().setTitle(selectedItem.getText());
        });
        drawerPresenter.addItem(drawerTab1);
        drawerPresenter.addItem(drawerTab2);
        drawerPresenter.addItem(drawerTab3);
        drawerPresenter.selectItem(drawerTab1);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
}
