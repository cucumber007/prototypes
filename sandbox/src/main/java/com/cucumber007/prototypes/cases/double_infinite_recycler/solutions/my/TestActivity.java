package com.cucumber007.prototypes.cases.double_infinite_recycler.solutions.my;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.cases.double_infinite_recycler.DataProvider;
import com.cucumber007.prototypes.cases.double_infinite_recycler.Payload;
import com.cucumber007.reusables.utils.logging.LogUtil;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.rv) RecyclerView rv;

    private MyArrayList<Payload> items = new MyArrayList<>();
    private DataProvider dataProvider = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        ButterKnife.bind(this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        TestAdapter adapter = new TestAdapter(this, items, R.layout.item_simple);
        rv.setAdapter(adapter);

        dataProvider.getDataForPage(0, lst -> {
            items.addAll(lst);
            adapter.notifyDataSetChanged();
            dataProvider.getDataForPage(1, lst1 -> {
                items.addAll(0, lst1);
                adapter.notifyItemRangeInserted(0, lst1.size());
                LogUtil.makeToast("inserted");
                Observable.timer(5000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                        .subscribe(tim -> {
                            items.removeRange(0, 10);
                            adapter.notifyItemRangeRemoved(0, 10);
                            LogUtil.makeToast("deleted");
                        });
            });
        });
    }

    private static class MyArrayList<T> extends ArrayList<T> {
        @Override
        public void removeRange(int fromIndex, int toIndex) {
            super.removeRange(fromIndex, toIndex);
        }
    }
}
