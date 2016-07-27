package com.cucumber007.prototypes;

import android.app.Application;
import android.content.Context;

import com.cucumber007.prototypes.activities._architecture.mvc.IDatabaseModel;
import com.cucumber007.prototypes.activities._architecture.mvc.SharedPrefsModel;

public class MyApplication extends Application{

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }


    public static Context getContext() {
        return context;
    }

    public static IDatabaseModel getDatabaseModel() {
        return new SharedPrefsModel(context);
    }
}
