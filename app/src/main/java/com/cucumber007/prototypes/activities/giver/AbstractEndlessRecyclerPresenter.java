package com.cucumber007.prototypes.activities.giver;

import android.support.v7.widget.RecyclerView;

public abstract class AbstractEndlessRecyclerPresenter {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    public AbstractEndlessRecyclerPresenter(RecyclerView recyclerView, RecyclerView.Adapter adapter, int visibleItems) {
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        init();
        recyclerView.addOnScrollListener(new RecyclerEndlessScrollListener(visibleItems, this::loadMore));
    }

    protected abstract void loadMore();

    protected abstract void init();


}
