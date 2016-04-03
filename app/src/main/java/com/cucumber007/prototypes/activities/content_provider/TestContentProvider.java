package com.cucumber007.prototypes.activities.content_provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.cucumber007.prototypes.database.DbHelper;
import com.cucumber007.prototypes.database.TestTable;

import java.util.HashMap;


public class TestContentProvider extends ContentProvider {

    // database
    private DbHelper database;

    // Uri codes used for the UriMacher
    private static final int TEST_ENTRY = 1;
    private static final int TEST_ENTRY_ID = 2;

    private static final String AUTHORITY = "com.cucumber007.prototypes.contentprovider";
    private static final String BASE_PATH = "testpath";

    private static final String TEST_ENTRY_PATH = BASE_PATH + "/" + TestTable.TABLE_NAME;

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY+ "/" + BASE_PATH);
    public static final Uri TEST_ENTRY_URI = Uri.parse("content://" + AUTHORITY + "/" + TEST_ENTRY_PATH);

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, TEST_ENTRY_PATH, TEST_ENTRY);
        sURIMatcher.addURI(AUTHORITY, TEST_ENTRY_PATH + "/#", TEST_ENTRY_ID);
    }

    @Override
    public boolean onCreate() {
        database = new DbHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        //projection map sets which columns to include in result
        HashMap<String, String> pillboxEventsJoinProjectionMap = new HashMap();
        pillboxEventsJoinProjectionMap.put(TestTable.COLUMN_ID, "PE" + "." + TestTable.COLUMN_ID + " as _id");
        pillboxEventsJoinProjectionMap.put(TestTable.COLUMN_NAME, TestTable.COLUMN_NAME);

        /*String join = PillboxEventsTable.TABLE_NAME+" as PE LEFT OUTER JOIN "+MedsTable.TABLE_NAME+" as MED"+
                " on PE."+PillboxEventsTable.COLUMN_MED_ID+" = MED."+MedsTable.COLUMN_ID;*/

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case TEST_ENTRY:
                queryBuilder.setTables(TestTable.TABLE_NAME);
                //queryBuilder.setProjectionMap(pillboxEventsJoinProjectionMap);
                break;
            case TEST_ENTRY_ID:
                queryBuilder.setTables(TestTable.TABLE_NAME);
                //queryBuilder.setProjectionMap(pillboxEventsJoinProjectionMap);
                /*queryBuilder.appendWhere(PillboxEventsTable.COLUMN_ID + "="
                        + uri.getLastPathSegment());*/
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);

        //todo wtf?
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case TEST_ENTRY:
                id = sqlDB.insert(TestTable.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case TEST_ENTRY:
                rowsDeleted = sqlDB.delete(TestTable.TABLE_NAME, selection,
                        selectionArgs);
                break;
            case TEST_ENTRY_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(TestTable.TABLE_NAME,
                                               TestTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(TestTable.TABLE_NAME,
                                               TestTable.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case TEST_ENTRY:
                rowsUpdated = sqlDB.update(TestTable.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case TEST_ENTRY_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(TestTable.TABLE_NAME,
                            values,
                                               TestTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(TestTable.TABLE_NAME,
                            values,
                                               TestTable.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

}
