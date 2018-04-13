package com.cucumber007.prototypes.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.List;

public abstract class SandboxItem implements Runnable {
    private Class clazz;
    private boolean isFragment;

    public SandboxItem(Class clazz) {
        this.clazz = clazz;
    }

    public boolean isFragment() {
        return Fragment.class.isAssignableFrom(clazz);
    }

    public Class getClazz() {
        return clazz;
    }

    public abstract String getName();

    public String getId() {
        return null;
    }

    @Override
    public String toString() {
        return getName();
    }
}
