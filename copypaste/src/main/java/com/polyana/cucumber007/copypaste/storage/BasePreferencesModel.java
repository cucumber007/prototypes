package com.polyana.cucumber007.copypaste.storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.polyana.cucumber007.copypaste.ContextApplication;


public class BasePreferencesModel {

    private static SharedPreferences preferences;

    public static SharedPreferences getPreferences() {
        if (preferences == null) {
            preferences = PreferenceManager.getDefaultSharedPreferences(ContextApplication.getContext());
        }
        return preferences;
    }

    public static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public BasePreferencesModel() {
        preferences = PreferenceManager.getDefaultSharedPreferences(ContextApplication.getContext());
    }

}
