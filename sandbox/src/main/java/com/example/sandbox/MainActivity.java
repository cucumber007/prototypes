package com.example.sandbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.status) TextView status;
    @Bind(R.id.counter) TextView counter;
    private boolean isLoading = false;
    private Subscription timerSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        status.setText("idle");
        timerSubscription = Observable.interval(1000, 3000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).subscribe(lng -> {

            if (!isLoading) {
                status.setText("loading");
                isLoading = true;
                SampleModel.getInstance().getData().subscribe(str -> {
                    isLoading = false;
                    status.setText("loaded");
                    counter.setText(str);
                });
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("counter", counter.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        String text = savedInstanceState.getString("counter");
        if (text != null) counter.setText(text);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (timerSubscription != null) timerSubscription.unsubscribe();
        super.onDestroy();
    }
}
