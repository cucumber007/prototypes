package com.cucumber007.prototypes.activities.content_provider;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.database.TestTable;

import butterknife.ButterKnife;


public class TestCursorAdapter extends CursorAdapter {

    private Context context;
    private OnCursorItemClickListener listener;

    public TestCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.checkable_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView) ButterKnife.findById(view, R.id.text))
                .setText(cursor.getString(cursor.getColumnIndexOrThrow(TestTable.COLUMN_NAME)));

        final int id = cursor.getInt(cursor.getColumnIndex(TestTable.COLUMN_ID));

        ButterKnife.findById(view, R.id.button11).setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(id);
            }
        });
    }

    @Override
    protected void onContentChanged() {
        super.onContentChanged();
    }


    public void setOnCursorItemClickListener(
            OnCursorItemClickListener listener) {
        this.listener = listener;
    }


    public interface OnCursorItemClickListener {
        void onClick(int id);
    }
}
