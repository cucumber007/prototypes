package com.example.sandbox;


import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

class SampleModel {

    private static final SampleModel ourInstance = new SampleModel();
    static SampleModel getInstance() {
        return ourInstance;
    }

    private int counter = 0;

    private SampleModel() {}

    public Observable<String> getData() {
        counter++;
        return Observable.timer(2000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).map(lng -> counter+"");
    }
}
