package com.cucumber007.prototypes.menu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.activities._architecture.mvc.MvcActivity;
import com.cucumber007.prototypes.activities._architecture.mvp.MvpActivity;
import com.cucumber007.prototypes.activities._libraries.butterknife.ButterknifeActivity;
import com.cucumber007.prototypes.activities._libraries.reactive_location.ReactiveLocationActivity;
import com.cucumber007.prototypes.activities._libraries.retrofit.RetrofitActivity;
import com.cucumber007.prototypes.activities._libraries.rxjava.RxJavaActivity;
import com.cucumber007.prototypes.activities._ui.activity_templates.BasicActivity;
import com.cucumber007.prototypes.activities._ui.coordinator.CoordinatorLayoutActivity;
import com.cucumber007.prototypes.activities._ui.custom_view.CustomViewActivity;
import com.cucumber007.prototypes.activities._ui.navigationDrawer.NavigationDrawerActivity;
import com.cucumber007.prototypes.activities._ui.recycler.RecyclerActivity;
import com.cucumber007.prototypes.activities._ui.viewpager.ViewPagerActivity;
import com.cucumber007.prototypes.activities._ui.views.ViewsActivity;
import com.cucumber007.prototypes.activities._ui.xml_drawables.XmlDrawableActivity;
import com.cucumber007.prototypes.activities.content_provider.ContentProviderActivity;
import com.cucumber007.prototypes.activities.files_and_loader.FilesAndLoaderActivity;
import com.cucumber007.prototypes.activities.graphics.BlurActivity;
import com.cucumber007.prototypes.activities.java.JavaCoreActivity;
import com.cucumber007.prototypes.activities.lambda.LambdaActivity;
import com.cucumber007.prototypes.activities.orientation.OrientationActivity;
import com.cucumber007.prototypes.activities.sandbox.SandboxActivity;

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

        //todo sort
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
        items.add(new MenuItem("Blur", BlurActivity.class));
        items.add(new MenuItem("Orientation", OrientationActivity.class));
        items.add(new MenuItem("ViewPager", ViewPagerActivity.class));
        items.add(new MenuItem("Android Studio templates", BasicActivity.class));
        items.add(new MenuItem("Location", ReactiveLocationActivity.class));

        listView.setAdapter(new MenuListAdapter(this, items));

    }
}
