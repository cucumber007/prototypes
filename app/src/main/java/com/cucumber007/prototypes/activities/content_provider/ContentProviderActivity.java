package com.cucumber007.prototypes.activities.content_provider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.database.TestTable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentProviderActivity extends Activity {

    @Bind(R.id.listView2) ListView listView2;
    @Bind(R.id.editText2) EditText editText2;

    private ContentResolver contentResolver;

    private Context context = this;

    //todo multi-choise list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        ButterKnife.bind(this);
        contentResolver = getContentResolver();

        Cursor cursor = contentResolver.query(TestContentProvider.TEST_ENTRY_URI, null, null, null, null);
        TestCursorAdapter adapter = new TestCursorAdapter(context, cursor, true);
        adapter.setOnCursorItemClickListener(
                id -> contentResolver.delete(TestContentProvider.TEST_ENTRY_URI, "_id = "+id, null));
        listView2.setAdapter(adapter);
    }


    @OnClick(R.id.button9)
    public void onClick() {
        ContentValues cv = new ContentValues();
        cv.put(TestTable.COLUMN_NAME, editText2.getText().toString());
        contentResolver.insert(TestContentProvider.TEST_ENTRY_URI, cv);
    }

}
