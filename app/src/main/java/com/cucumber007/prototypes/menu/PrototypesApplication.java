package com.cucumber007.prototypes.menu;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.cucumber007.prototypes.sandbox.room.AppDatabase;
import com.cucumber007.reusables.ContextApplication;

import static java.security.AccessController.getContext;

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
