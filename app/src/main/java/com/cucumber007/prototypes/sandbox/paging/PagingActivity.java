package com.cucumber007.prototypes.sandbox.paging;

import android.arch.lifecycle.Observer;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.paging.PositionalDataSource;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cucumber007.prototypes.R;


import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

public class PagingActivity extends AppCompatActivity {

    @BindView(R.id.recycler) RecyclerView recycler;

    private PagingAdapter pagingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paging);
        ButterKnife.bind(this);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        pagingAdapter = new PagingAdapter(this, new DiffCallback<String>() {
            @Override
            public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
                return oldItem.equals(newItem);
            }
        });
        recycler.setAdapter(pagingAdapter);
        LivePagedListBuilder<Integer, String> livePagedListBuilder = new LivePagedListBuilder<>(() -> new PositionalDataSource<String>() {
            @Override
            public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback callback) {
                callback.onResult(Observable.just(Math.random()+"").repeat(params.pageSize).toList().toBlocking().first(), 0, 50);
            }

            @Override
            public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback callback) {
                callback.onResult(Observable.just(Math.random()+"").repeat(params.loadSize).toList().toBlocking().first());
            }
        }, 5);

        livePagedListBuilder.build().observe(this, strings -> pagingAdapter.setList(strings));


    }
}
