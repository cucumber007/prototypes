package com.cucumber007.prototypes.sandbox.layout_manager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.cucumber007.prototypes.R;
import com.cucumber007.reusables.recycler.ArrayRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

public class LayoutManagerActivity extends AppCompatActivity {

    @BindView(R.id.recycler) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_manager);
        ButterKnife.bind(this);

        //RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        RecyclerView.ItemDecoration dividerItemDecoration = new MyItemDecoration(this);
        recyclerView.addItemDecoration(dividerItemDecoration);

        Observable.range(1, 15).map(num -> "Element " + num).toList().subscribe(list -> {
            recyclerView.setAdapter(new ArrayRecyclerAdapter<>(this, list, R.layout.item_test_layout, R.id.text));
            recyclerView.setLayoutManager(new TestLayoutManager(this));
            //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        });

    }
}
