package com.cucumber007.prototypes.activities._libraries.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.cucumber007.prototypes.R;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;

public class RxJavaActivity extends Activity {

    @Bind(R.id.outputText) TextView outputText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        ButterKnife.bind(this);

        Observable.interval(2000, TimeUnit.MILLISECONDS).flatMap(lng -> Observable.just("normal "+lng))
                .timeout(1000, TimeUnit.MILLISECONDS, Observable.just("timeout"))
                .subscribe(text -> Log.d("cutag", text));

    }


    private void print(String text) {
        outputText.setText(outputText.getText() + "\n" + text);
    }
}
