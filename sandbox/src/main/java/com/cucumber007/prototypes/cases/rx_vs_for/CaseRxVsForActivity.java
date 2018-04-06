package com.cucumber007.prototypes.cases.rx_vs_for;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.cases.PerformanceTester;

import rx.Observable;

public class CaseRxVsForActivity extends AppCompatActivity {

    private CaseRxVsForActivity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_rx_vs_for);

        int size = 500;

        new PerformanceTester() {
            @Override
            public void run() {
                Observable.range(0, size).doOnNext(context::doSomething).subscribe();
            }

            @Override
            public String getName() {
                return "Rx";
            }
        }.test();

        new PerformanceTester() {
            @Override
            public void run() {
                for (int i = 0; i < size; i++) {
                    doSomething(i);
                }
            }

            @Override
            public String getName() {
                return "For";
            }
        }.test();
    }

    private void doSomething(Object object) {
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
