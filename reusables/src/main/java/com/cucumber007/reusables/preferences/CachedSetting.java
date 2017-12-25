package com.cucumber007.reusables.preferences;

public abstract class CachedSetting<T> implements Setting<T> {

    private T cached;

    @Override
    public void save(T object) {
        cached = object;
        saveNew(object);
    }

    protected abstract void saveNew(T object);
    protected abstract T loadNew();

    @Override
    public T load() {
        if (cached == null) {
            cached = loadNew();
        }
        return cached;
    }
}
