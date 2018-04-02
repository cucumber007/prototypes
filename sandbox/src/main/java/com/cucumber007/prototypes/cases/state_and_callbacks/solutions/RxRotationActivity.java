package com.cucumber007.prototypes.cases.state_and_callbacks.solutions;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.cases.SampleDataProvider;
import com.cucumber007.reusables.utils.logging.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static java.lang.Thread.sleep;

public class RxRotationActivity extends AppCompatActivity {

    @BindView(R.id.imageView) ImageView imageView;

    private static int counter;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callbacks);
        ButterKnife.bind(this);

        counter++;
        int number = counter;
        subscription = Observable.just(number).map(num -> {
            LogUtil.logDebug("Starting "+number);
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return SampleDataProvider.getBitmap(2000, 2000);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onBitmapReady);

    }

    public void onBitmapReady(Bitmap bitmap) {
        LogUtil.logDebug("Ready");
        imageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null) {
            subscription.unsubscribe();
            //LogUtil.logDebug("Destroy ", asyncTask.number);
        }
    }
}
