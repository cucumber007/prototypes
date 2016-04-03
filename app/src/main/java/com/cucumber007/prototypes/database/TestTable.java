package com.cucumber007.prototypes.database;

import android.database.sqlite.SQLiteDatabase;

public class TestTable {

    // Database table
    public static final String TABLE_NAME = "meds";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    // Database creation sql statement
    private static final String TABLE_CREATE = "create table "
            + TABLE_NAME + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text "
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

}
