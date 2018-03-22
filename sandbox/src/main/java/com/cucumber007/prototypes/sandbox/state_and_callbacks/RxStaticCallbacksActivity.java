package com.cucumber007.prototypes.sandbox.state_and_callbacks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cucumber007.prototypes.R;
import com.cucumber007.reusables.utils.logging.LogUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static java.lang.Thread.sleep;

public class RxStaticCallbacksActivity extends AppCompatActivity {

    @BindView(R.id.text) TextView text;
    @BindView(R.id.large) TextView large;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_static_callbacks);
        ButterKnife.bind(this);

        LogUtil.setDebugMode(true);
        LogUtil.logDebug("onCreate");

        /*new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                LogUtil.logDebug("Task");
                text.setText("lol");
            }
        }.execute();*/

        Observable.interval(2000, TimeUnit.MILLISECONDS, Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lng -> {
                    LogUtil.logDebug("Timer");
                    text.setText(lng+"");
                    large.setText(SampleDataProvider.getString(1000));
                });
    }

    @Override
    protected void onDestroy() {
        LogUtil.logDebug("onDestroy");
        super.onDestroy();
    }
}
