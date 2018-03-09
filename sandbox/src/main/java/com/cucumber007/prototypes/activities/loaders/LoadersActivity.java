package com.cucumber007.prototypes.activities.loaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.cucumber007.prototypes.R;

public class LoadersActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int LOADER_ID = 0;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaders);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    public void restartLoader() {
        getSupportLoaderManager().restartLoader(0, null, this);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Loader
    ///////////////////////////////////////////////////////////////////////////


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String select = "some SQL";
        return new CursorLoader(context, Uri.EMPTY, null, select, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //do stuff with data
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //reload
    }
}
