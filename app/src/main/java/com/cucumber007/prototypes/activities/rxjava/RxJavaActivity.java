package com.cucumber007.prototypes.activities.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.cucumber007.prototypes.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

public class RxJavaActivity extends Activity {

    @Bind(R.id.outputText) TextView outputText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        ButterKnife.bind(this);

        Observable<String> myObservable = Observable.create(sub -> {
            sub.onNext("Hello, world!");
            sub.onCompleted();
        });

        Observable<String> myObservable1 = Observable.just("Hello, world!");

        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                print(s);
            }


            @Override
            public void onCompleted() {
            }


            @Override
            public void onError(Throwable e) {
            }
        };


        myObservable.subscribe(mySubscriber);
        myObservable1.subscribe((s) -> print(s));

    }


    private void print(String text) {
        outputText.setText(outputText.getText() + "\n" + text);
    }
}
