package com.cucumber007.prototypes.menu;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.cucumber007.prototypes.sandbox.room.AppDatabase;
import com.polyana.cucumber007.copypaste.ContextApplication;

public class PrototypesApplication extends ContextApplication {

    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        getDatabase();
    }

    public static AppDatabase getDatabase() {
        if (database == null) {
            database = Room.databaseBuilder(getContext(),
                    AppDatabase.class, "database-name").build();
        }
        return database;
    }
}
