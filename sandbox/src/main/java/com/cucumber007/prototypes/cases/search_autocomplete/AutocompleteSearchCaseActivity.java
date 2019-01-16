package com.cucumber007.prototypes.cases.search_autocomplete;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.cases.SampleDataProvider;
import com.cucumber007.reusables.listeners.LoadingListener;
import com.cucumber007.reusables.recycler.adapters.ArrayRecyclerAdapter;
import com.cucumber007.reusables.utils.logging.LogUtil;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AutocompleteSearchCaseActivity extends AppCompatActivity implements LoadingListener {

    @BindView(R.id.et_search) EditText etSearch;
    @BindView(R.id.rv_results) RecyclerView rvResults;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    private ArrayRecyclerAdapter<String> adapter;

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autocomplete_search_case);
        ButterKnife.bind(this);

        rvResults.setLayoutManager(new LinearLayoutManager(this));
        rvResults.setAdapter(adapter = new ArrayRecyclerAdapter<String>(this));

        RxTextView.afterTextChangeEvents(etSearch)
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .map(ev -> ev.editable().toString())
                .filter(str -> str.length() > 0)
                .doOnNext(str -> onStartLoading())
                .switchMapSingle(
                        str -> SampleDataProvider.search(str)
                        .onErrorResumeNext(throwable -> {
                            LogUtil.makeToast("Connection error");
                            onStopLoading();
                            return Single.never();
                        }))
                .subscribe(lst -> {
                    if (lst != null) {
                        adapter.replaceItemsAndUpdate(lst);
                        onStopLoading();
                    }
                });
        
    }

    @Override
    public void onStartLoading() {
        rvResults.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopLoading() {
        rvResults.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

}
