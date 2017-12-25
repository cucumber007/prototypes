package com.cucumber007.prototypes.menu;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.cucumber007.prototypes.sandbox.room.AppDatabase;
import com.polyana.cucumber007.copypaste.ContextApplication;

public class PrototypesApplication extends ContextApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
    }
}
