package com.cucumber007.prototypes.cases.double_infinite_recycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.cases.double_infinite_recycler.solutions.DoubleEndlessRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DoubleEndlessRecyclerCaseActivity extends AppCompatActivity {

    @BindView(R.id.rv) RecyclerView rv;
    @BindView(R.id.tv_data) TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_endless_recycler_case);
        ButterKnife.bind(this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        DoubleEndlessRecyclerAdapter adapter = new DoubleEndlessRecyclerAdapter(this,
                R.layout.item_simple, new DataProvider());
        adapter.setDataListener(str -> tvData.setText(str));
        rv.setAdapter(adapter);
    }
}
