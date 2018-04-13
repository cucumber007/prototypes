package com.cucumber007.prototypes.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.activities._libraries.butterknife.ButterknifeActivity;
import com.cucumber007.prototypes.activities._libraries.retrofit.RetrofitActivity;
import com.cucumber007.prototypes.activities._ui.activity_templates.BasicActivity;
import com.cucumber007.prototypes.activities._ui.coordinator.CoordinatorLayoutActivity;
import com.cucumber007.prototypes.activities._ui.custom_view.CustomViewActivity;
import com.cucumber007.prototypes.activities._ui.navigationDrawer.NavigationDrawerActivity;
import com.cucumber007.prototypes.activities._ui.recycler.RecyclerActivity;
import com.cucumber007.prototypes.activities._ui.tabs.TabsActivity;
import com.cucumber007.prototypes.activities._ui.viewpager.PagerActivity;
import com.cucumber007.prototypes.activities._ui.views.ViewsActivity;
import com.cucumber007.prototypes.activities._ui.xml_drawables.XmlDrawableActivity;
import com.cucumber007.prototypes.activities.content_provider.ContentProviderActivity;
import com.cucumber007.prototypes.activities.fragments_sandbox.FragmentSandboxActivity;
import com.cucumber007.prototypes.activities.graphics.BlurActivity;
import com.cucumber007.prototypes.activities.java.JavaCoreActivity;
import com.cucumber007.prototypes.activities.loaders.FilesAndLoaderActivity;
import com.cucumber007.prototypes.activities.orientation.OrientationActivity;
import com.cucumber007.prototypes.cases.Case;
import com.cucumber007.prototypes.cases.backpressure.BackpressureCase;
import com.cucumber007.prototypes.sandbox.room.RoomActivity;
import com.cucumber007.prototypes.sandbox.rxjava.RxJavaActivity;
import com.cucumber007.reusables.recycler.ArrayRecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @BindView(R.id.listView) ListView listView;
    @BindView(R.id.rv_menu) RecyclerView rvMenu;

    private SandboxItemsModel sandboxItemsModel = SandboxItemsModel.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        ArrayRecyclerAdapter<SandboxItem> adapter = new ArrayRecyclerAdapter<>(this, new ArrayList<>(
                SandboxItemsModel.getInstance().getAllItems()));
        adapter.setItemClickListener((position, view, item1) -> {
            SandboxItem sandboxItem = (SandboxItem) item1;
            if(sandboxItem.isFragment()) {
                startActivity(new Intent(this, SandboxItemActivity.class)
                    .putExtra(SandboxItemActivity.class.getName(), sandboxItem.getId()));
            } else {
                startActivity(new Intent(this, sandboxItem.getClazz()));
            }
        });
        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        rvMenu.setAdapter(adapter);





        //*********** OLD **********************

        //todo sort
        //todo hierarchy
        List<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem("RecyclerView", RecyclerActivity.class));
        items.add(new MenuItem("Views / Styles", ViewsActivity.class));
        items.add(new MenuItem("Custom Views", CustomViewActivity.class));
        items.add(new MenuItem("Navigation Drawer", NavigationDrawerActivity.class));
        items.add(new MenuItem("Coordinator Layout", CoordinatorLayoutActivity.class));
        items.add(new MenuItem("Fragments", FragmentSandboxActivity.class));
        items.add(new MenuItem("ViewPager", PagerActivity.class));
        items.add(new MenuItem("Tabs", TabsActivity.class));
        items.add(new MenuItem());
        items.add(new MenuItem("ButterKnife", ButterknifeActivity.class));
        items.add(new MenuItem("RxJava", RxJavaActivity.class));
        items.add(new MenuItem("Retrofit", RetrofitActivity.class));
        items.add(new MenuItem("Java Core", JavaCoreActivity.class));
        items.add(new MenuItem());
        items.add(new MenuItem("XML Drawables", XmlDrawableActivity.class));
        items.add(new MenuItem("Blur", BlurActivity.class));
        items.add(new MenuItem("Orientation", OrientationActivity.class));
        items.add(new MenuItem());
        items.add(new MenuItem("Files and Loader", FilesAndLoaderActivity.class));
        items.add(new MenuItem("Content Provider", ContentProviderActivity.class));
        items.add(new MenuItem("Room", RoomActivity.class));
        items.add(new MenuItem());
        items.add(new MenuItem("Android Studio templates", BasicActivity.class));
        items.add(new MenuItem());

        listView.setAdapter(new MenuListAdapter(this, items));


    }



}
