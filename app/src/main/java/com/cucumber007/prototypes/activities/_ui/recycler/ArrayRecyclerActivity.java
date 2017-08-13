package com.cucumber007.prototypes.activities._ui.recycler;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.reusables.recycler.ArrayRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArrayRecyclerActivity extends AppCompatActivity {

    @BindView(R.id.recycler) RecyclerView recycler;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_recycler);
        ButterKnife.bind(this);

        recycler.setLayoutManager(new LinearLayoutManager(context));
        recycler.setAdapter(new ArrayRecyclerAdapter(context, new String[] {"One", "Two", "Three"}));
    }
}
