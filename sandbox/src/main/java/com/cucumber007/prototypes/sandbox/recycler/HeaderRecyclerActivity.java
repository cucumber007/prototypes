package com.cucumber007.prototypes.sandbox.recycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cucumber007.prototypes.R;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeaderRecyclerActivity extends AppCompatActivity {

    @BindView(R.id.recycler) RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_recycler);
        ButterKnife.bind(this);

        recycler.setAdapter(new HeaderAdapter(this, Arrays.asList("One", "Two", "Three"), R.layout.item_test_layout, R.layout.item_menu_list));
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
