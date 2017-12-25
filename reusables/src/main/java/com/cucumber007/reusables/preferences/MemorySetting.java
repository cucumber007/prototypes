package com.cucumber007.reusables.preferences;

public class MemorySetting<T> implements Setting<T> {
    private T value;

    @Override
    public String getKey() {
        return "key";
    }

    @Override
    public void save(T object) {
        value = object;
    }

    @Override
    public T load() {
        return value;
    }
}
