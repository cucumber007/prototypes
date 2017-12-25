package com.cucumber007.reusables.preferences;

public interface Setting<T> {
    String getKey();
    void save(T object);
    T load();
}
