package com.cucumber007.prototypes.cases.double_infinite_recycler;

import com.cucumber007.prototypes.cases.state_and_callbacks.SampleDataProvider;
import com.cucumber007.reusables.utils.Callback;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DataProvider {

    public static final int PAGE_SIZE = 10;
    public static final int TIMEOUT = 1000;

    public void getDataForPage(int page, Callback<List<Payload>> callback) {
        Observable.timer(TIMEOUT, TimeUnit.MILLISECONDS, Schedulers.io())
                .flatMap(tim -> Observable.range(0, PAGE_SIZE))
                .map(num -> "Data "+(page*PAGE_SIZE+num))
                .map(name -> new Payload(name, SampleDataProvider.getString(10000)))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback::onReady);
    }

    public int getPageSize() {
        return PAGE_SIZE;
    }
}
