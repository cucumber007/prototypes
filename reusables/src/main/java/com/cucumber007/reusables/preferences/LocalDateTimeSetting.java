package com.cucumber007.reusables.preferences;

import android.content.SharedPreferences;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

public abstract class LocalDateTimeSetting implements Setting<LocalDateTime> {
    private SharedPreferences preferences;

    public LocalDateTimeSetting(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public void save(LocalDateTime object) {
        preferences.edit().putLong(getKey(), object.toEpochSecond(ZoneOffset.UTC)).commit();
    }

    @Override
    public LocalDateTime load() {
        long seconds = preferences.getLong(getKey(), -1);
        if(seconds < 0) return null;
        else return LocalDateTime.ofEpochSecond(seconds, 0, ZoneOffset.UTC);
    }
}
