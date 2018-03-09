package com.cucumber007.prototypes.activities._ui.activity_templates;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.menu.MenuItem;
import com.cucumber007.prototypes.menu.MenuListAdapter;

import java.util.ArrayList;
import java.util.List;

public class BasicActivity extends AppCompatActivity {


    //todo check how it all works

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        ListView listView = (ListView)findViewById(R.id.listView);

        List<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem("FullScreen", FullscreenActivity.class));
        items.add(new MenuItem("Detail", ItemListActivity.class));
        items.add(new MenuItem("Login", LoginActivity.class));
        items.add(new MenuItem("Scrolling", ScrollingActivity.class));
        items.add(new MenuItem("Settings", SettingsActivity.class));
        items.add(new MenuItem("Tabbed", TabbedActivity.class));


        listView.setAdapter(new MenuListAdapter(this, items));
    }

}
