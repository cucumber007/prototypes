package com.cucumber007.reusables.tabs;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class TabsPresenter {

    private Context context;
    private FragmentManager fragmentManager;
    private int containerId;
    private ArrayMap<Integer, AbstractTabFragment> fragmentsMap = new ArrayMap<>();

    public TabsPresenter(AppCompatActivity context, int containerId) {
        this.context = context;
        fragmentManager = context.getSupportFragmentManager();
        this.containerId = containerId;
    }

    public void addTab(Integer id, AbstractTabFragment fragment) {
        fragmentsMap.put(id, fragment);
    }

    public void onViewClicked(Integer id) {
        switchFragment(fragmentsMap.get(id));
    }

    private void switchFragment(AbstractTabFragment fragment) {
        fragmentManager.beginTransaction().replace(containerId, fragment).commit();
    }

}
