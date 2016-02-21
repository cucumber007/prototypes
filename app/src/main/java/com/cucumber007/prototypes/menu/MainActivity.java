package com.cucumber007.prototypes.menu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.activities.recycler.RecyclerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @Bind(R.id.listView) ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        List<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem("Test", RecyclerActivity.class));

        listView.setAdapter(new MenuListAdapter(this, items));

    }
}
