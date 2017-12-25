package com.cucumber007.reusables.preferences;

import android.content.SharedPreferences;

public abstract class BooleanPreference implements Setting<Boolean> {

    private SharedPreferences preferences;

    public BooleanPreference(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public void save(Boolean object) {
        preferences.edit().putBoolean(getKey(), object).apply();
    }

    @Override
    public Boolean load() {
        return preferences.getBoolean(getKey(), false);
    }

}
