package com.cucumber007.reusables.preferences;

import android.content.SharedPreferences;

public abstract class StringPreference implements Setting<String> {

    private SharedPreferences preferences;

    public StringPreference(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public void save(String object) {
        preferences.edit().putString(getKey(), object).apply();
    }

    @Override
    public String load() {
        return preferences.getString(getKey(), null);
    }
}
