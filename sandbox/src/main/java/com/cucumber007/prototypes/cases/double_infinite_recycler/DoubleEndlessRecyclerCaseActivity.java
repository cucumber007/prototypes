package com.cucumber007.prototypes.cases.double_infinite_recycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.cases.SampleDataProvider;
import com.cucumber007.prototypes.cases.double_infinite_recycler.solutions.my.DoubleEndlessRecyclerAdapter;
import com.cucumber007.prototypes.cases.double_infinite_recycler.solutions.shkur.PageProvider;
import com.cucumber007.prototypes.cases.double_infinite_recycler.solutions.shkur.PagesProviderRecyclerViewAdapter;
import com.cucumber007.prototypes.cases.double_infinite_recycler.solutions.shkur.TwoWayEndlessPagesProviderScrollListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DoubleEndlessRecyclerCaseActivity extends AppCompatActivity {

    @BindView(R.id.rv) RecyclerView rv;
    @BindView(R.id.tv_data) TextView tvData;
    @BindView(R.id.rv_shkur) RecyclerView rvShkur;
    @BindView(R.id.tv_data_shkur) TextView tvDataShkur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_endless_recycler_case);
        ButterKnife.bind(this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        DoubleEndlessRecyclerAdapter adapter = new DoubleEndlessRecyclerAdapter(this, rv,
                R.layout.item_simple, new SampleDataProvider());
        adapter.setDataListener(str -> tvData.setText(str));
        rv.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvShkur.setLayoutManager(layoutManager);
        PagesProviderRecyclerViewAdapter adapter1 = new PagesProviderRecyclerViewAdapter();
        rvShkur.setAdapter(adapter1);
        PageProvider provider = new PageProvider();
        rvShkur.addOnScrollListener(new TwoWayEndlessPagesProviderScrollListener(adapter1, layoutManager, provider));
        provider.request(0, ints -> runOnUiThread(() -> adapter1.initValues(ints)));
        adapter1.setDataListener(str -> tvDataShkur.setText(str));
    }
}
