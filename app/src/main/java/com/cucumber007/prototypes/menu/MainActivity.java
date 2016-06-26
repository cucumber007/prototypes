package com.cucumber007.prototypes.menu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.activities.butterknife.ButterknifeActivity;
import com.cucumber007.prototypes.activities.content_provider.ContentProviderActivity;
import com.cucumber007.prototypes.activities.coordinator.CoordinatorLayoutActivity;
import com.cucumber007.prototypes.activities.custom_view.CustomViewActivity;
import com.cucumber007.prototypes.activities.files_and_loader.FilesAndLoaderActivity;
import com.cucumber007.prototypes.activities.java.JavaCoreActivity;
import com.cucumber007.prototypes.activities.lambda.LambdaActivity;
import com.cucumber007.prototypes.activities.mvc.MvcActivity;
import com.cucumber007.prototypes.activities.mvp.MvpActivity;
import com.cucumber007.prototypes.activities.navigationDrawer.NavigationDrawerActivity;
import com.cucumber007.prototypes.activities.recycler.RecyclerActivity;
import com.cucumber007.prototypes.activities.retrofit.RetrofitActivity;
import com.cucumber007.prototypes.activities.rxjava.RxJavaActivity;
import com.cucumber007.prototypes.activities.sandbox.SandboxActivity;
import com.cucumber007.prototypes.activities.views.ViewsActivity;
import com.cucumber007.prototypes.activities.xml_drawables.XmlDrawableActivity;

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
        items.add(new MenuItem("RecyclerView", RecyclerActivity.class));
        items.add(new MenuItem("ButterKnife", ButterknifeActivity.class));
        items.add(new MenuItem("MVC", MvcActivity.class));
        items.add(new MenuItem("Lambda", LambdaActivity.class));
        items.add(new MenuItem("RxJava", RxJavaActivity.class));
        items.add(new MenuItem("Retrofit", RetrofitActivity.class));
        items.add(new MenuItem("Views / Styles", ViewsActivity.class));
        items.add(new MenuItem("XML Drawables", XmlDrawableActivity.class));
        items.add(new MenuItem("Files and Loader", FilesAndLoaderActivity.class));
        items.add(new MenuItem("Content Provider", ContentProviderActivity.class));
        items.add(new MenuItem("Java Core", JavaCoreActivity.class));
        items.add(new MenuItem("Sandbox", SandboxActivity.class));
        items.add(new MenuItem("MVP", MvpActivity.class));
        items.add(new MenuItem("Custom Views", CustomViewActivity.class));
        items.add(new MenuItem("Navigation Drawer", NavigationDrawerActivity.class));
        items.add(new MenuItem("Coordinator Layout", CoordinatorLayoutActivity.class));

        listView.setAdapter(new MenuListAdapter(this, items));

    }
}
