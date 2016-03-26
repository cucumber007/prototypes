package com.cucumber007.prototypes.activities.recycler;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cucumber007.prototypes.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerActivity extends Activity {

    //todo animation
    //todo more layout managers
    //todo custom libraries

    @Bind(R.id.recycler) RecyclerView recycler;

    private Context context = this;
    private boolean direction = true;
    private int lm = 0;

    private List<String> list = new ArrayList<>();
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        ButterKnife.bind(this);

        for (int i = 0; i < 10; i++) {
            list.add("Element " + i);
        }

        adapter = new RecyclerAdapter(this, list);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        recycler.addOnScrollListener(new RecyclerEndlessScrollListener(new RecyclerEndlessScrollListener.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                for (int i = 0; i < 10; i++) {
                    list.add("New element "+i);
                }
                adapter.notifyDataSetChanged();
            }
        }));

    }

    @OnClick(R.id.direction_button)
    void click() {
        direction = !direction;
        recycler.setLayoutManager(
            new LinearLayoutManager(context, direction ?
                    LinearLayoutManager.VERTICAL :
                    LinearLayoutManager.HORIZONTAL , false)
        );
    }

    @OnClick(R.id.lm_button)
    void click1() {
        RecyclerView.LayoutManager manager;
        lm++;
        if(lm > 1) lm = 0;
        switch (lm) {
            case 1:
                manager = new GridLayoutManager(context, 3);
                break;
            default: manager = new LinearLayoutManager(context);
        }
        recycler.setLayoutManager(manager);

    }

}
