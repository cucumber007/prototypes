package com.cucumber007.prototypes.activities.rxjava;

import android.app.Activity;
import android.os.Bundle;

import com.cucumber007.prototypes.R;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class RxJavaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        //Observable.just("Hello, world!").su

        Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("Hello, world!");
                        sub.onCompleted();
                    }
                }
        );

        Observable.just("Hello, world!")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });

        Observable.just("Hello, world!")
                .subscribe(s -> System.out.println(s));

    }
}
