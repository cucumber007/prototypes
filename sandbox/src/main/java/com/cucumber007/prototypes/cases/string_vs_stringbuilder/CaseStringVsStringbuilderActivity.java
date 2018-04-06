package com.cucumber007.prototypes.cases.string_vs_stringbuilder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cucumber007.prototypes.R;
import com.cucumber007.reusables.utils.logging.LogUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CaseStringVsStringbuilderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_string_vs_stringbuilder);

        test(1000);
        test(5000);
        test(10000);
        

    }

    private void test(int size) {
        LogUtil.logDebug("******Size", size);
        long start = System.currentTimeMillis();
        String s = "";
        for (int i = 0; i < size; i++) {
            s += String.valueOf(i);
        }
        LogUtil.logDebug("String: ", System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(String.valueOf(i));
        }
        String s1 = sb.toString();
        LogUtil.logDebug("StringBuilder: ", System.currentTimeMillis() - start);
    }
}
