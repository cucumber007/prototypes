package com.cucumber007.reusables.tabs;


import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;

public class DrawerPresenter<T extends View> {

    private SparseArray<T> items = new SparseArray<>();
    private OnItemSelectedListener<T> onItemSelectedListener;
    private int selectedId = -1;

    public DrawerPresenter(OnItemSelectedListener<T> onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public void addItem(int id, T item) {
        items.append(id, item);
        item.setOnClickListener(view -> selectItem(id));
    }

    private T getDrawerItemById(int id) {
        return items.get(id);
    }


    public void selectItem(int id) {
        onItemSelectedListener.onSelected(id, getDrawerItemById(id), selectedId, getDrawerItemById(selectedId));
        selectedId = id;
    }

    public interface OnItemSelectedListener<T> {
        void onSelected(int selectedId, T selectedItem, int lastSelectedId, @Nullable T lastSelectedItem);
    }
}
