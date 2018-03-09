package com.cucumber007.prototypes.sandbox.selectable_recycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cucumber007.prototypes.R;
import com.cucumber007.reusables.recycler.SelectableRecyclerAdapter;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectableRecyclerActivity extends AppCompatActivity {

    @BindView(R.id.rv) RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectable_recycler);
        ButterKnife.bind(this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new SelectableRecyclerAdapter<>(this, rv,
                Arrays.asList("One", "Two"), R.layout.item_selectable));
    }
}
