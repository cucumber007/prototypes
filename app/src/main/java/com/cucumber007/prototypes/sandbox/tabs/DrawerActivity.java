package com.cucumber007.prototypes.sandbox.tabs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cucumber007.prototypes.R;

public class DrawerActivity extends AppCompatActivity {

    private DrawerPresenter<TextView> drawerPresenter;
    private TabsPresenter tabsPresenter;

    public static final int TAB_ADS = 0;
    public static final int TAB_CHAT = 1;
    public static final int TAB_SETTINGS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        tabsPresenter = new TabsPresenter();
        tabsPresenter.addLayout(TAB_ADS, adsLayout);
        tabsPresenter.addLayout(TAB_CHAT, chatLayout);
        tabsPresenter.addLayout(TAB_SETTINGS, settingsLayout);
    }
}
