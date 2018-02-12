package com.cucumber007.reusables.tabs;


import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DrawerPresenter<T extends View> {

    private List<T> items = new ArrayList<>();
    private OnItemSelectedListener<T> onItemSelectedListener;
    private T selectedView;

    public DrawerPresenter(OnItemSelectedListener<T> onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public void addItem(T view) {
        items.add(view);
        view.setOnClickListener(view1 -> selectItem((T)view1));
    }

    private T getDrawerItemById(int id) {
        return items.get(id);
    }


    public void selectItem(T view) {
        onItemSelectedListener.onSelected(view, selectedView);
        selectedView = view;
    }

    public interface OnItemSelectedListener<T> {
        void onSelected(T selectedItem, @Nullable T lastSelectedItem);
    }
}
