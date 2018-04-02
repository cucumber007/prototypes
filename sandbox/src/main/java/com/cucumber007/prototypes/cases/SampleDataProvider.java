package com.cucumber007.prototypes.cases;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.cucumber007.reusables.utils.Callback;
import com.cucumber007.reusables.utils.logging.LogUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SampleDataProvider {

    public static String getString(int size) {
        byte byt = 'A';
        byte[] bytes = new byte[size];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = byt;
        }
        return new String(bytes);
    }

    public static Bitmap getBitmap(int width, int height) {
        Paint paint = new Paint();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawARGB(255, 200, 200, 200);
        canvas.drawCircle(width/2, height/2, (float)(Math.min(width, height)*Math.random()), paint);
        return bitmap;
    }

    public static final int PAGE_SIZE = 10;
    public static final int TIMEOUT = 1000;

    public static void getDataForPage(int page, Callback<List<Payload>> callback) {
        Observable.timer(TIMEOUT, TimeUnit.MILLISECONDS, Schedulers.io())
                .flatMap(tim -> Observable.range(0, PAGE_SIZE))
                .map(num -> "Data "+(page*PAGE_SIZE+num))
                .map(name -> new Payload(name, com.cucumber007.prototypes.cases.SampleDataProvider.getString(10000)))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback::onReady);
    }

    public static int getPageSize() {
        return PAGE_SIZE;
    }

    public static Single<List<String>> search(String query) {
        LogUtil.logDebug("search", query);
        return io.reactivex.Observable.timer(500, TimeUnit.MILLISECONDS, io.reactivex.schedulers.Schedulers.io())
                .flatMap(tim -> io.reactivex.Observable.range(query.length(), query.length() + 10))
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .map(num -> "Data " + num).toList()
                .doOnSuccess(it -> {
                            double randValue = Math.random();
                            if (randValue < 0.3) {
                                LogUtil.logDebug("network error: "+randValue);
                                throw new RuntimeException("network");
                            }
                        }
                );
    }
}
