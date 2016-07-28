package com.cucumber007.prototypes.activities._ui.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.cucumber007.prototypes.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GridViewActivity extends AppCompatActivity {

    @Bind(R.id.gridView) GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        ButterKnife.bind(this);

        String[] data = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"};

        GridView gvMain;
        ArrayAdapter<String> adapter;


            adapter = new ArrayAdapter<String>(this, R.layout.item_menu_list, R.id.text, data);
            gridView.setAdapter(adapter);

    }
}
