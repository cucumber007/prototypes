package com.cucumber007.prototypes.menu;

import android.support.v4.util.ArrayMap;

import com.cucumber007.prototypes.cases.backpressure.BackpressureCase;

import java.util.ArrayList;
import java.util.List;

public class SandboxItemsModel {
    private static final SandboxItemsModel ourInstance = new SandboxItemsModel();
    public static SandboxItemsModel getInstance() {
        return ourInstance;
    }

    public SandboxItemsModel() {
        addItem(new BackpressureCase());
    }

    private ArrayMap<String, SandboxItem> menuMap = new ArrayMap<>();

    public SandboxItem getItemById(String id) {
        return menuMap.get(id);
    }

    public List<SandboxItem> getAllItems() {
        //todo optimize?
        return new ArrayList<>(menuMap.values());
    }

    private void addItem(SandboxItem sandboxItem) {
        menuMap.put(sandboxItem.getId(), sandboxItem);
    }

}
