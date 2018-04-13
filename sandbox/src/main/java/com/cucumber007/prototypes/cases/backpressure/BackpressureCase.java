package com.cucumber007.prototypes.cases.backpressure;

import android.support.design.widget.AppBarLayout;

import com.cucumber007.prototypes.cases.Case;
import com.cucumber007.prototypes.cases.CaseFragment;
import com.cucumber007.reusables.utils.logging.LogUtil;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.processors.ReplayProcessor;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class BackpressureCase extends Case {

    @Override
    public void run() {
        getFlowable().subscribe(in -> {
            sleep(1000);
            LogUtil.logDebug("", in);
        });
    }

    public Flowable getFlowable() {
        PublishProcessor<Integer> behaviorSubject = PublishProcessor.create();

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 100000; i++) {
                    behaviorSubject.onNext(i);
                }
                super.run();
            }
        }.start();

        return behaviorSubject/*.onBackpressureBuffer()*/.observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String getName() {
        return "Backpressure";
    }
}
