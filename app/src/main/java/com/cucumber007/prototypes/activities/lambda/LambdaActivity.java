package com.cucumber007.prototypes.activities.lambda;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.cucumber007.prototypes.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LambdaActivity extends Activity {


    @Bind(R.id.ordinalText) TextView ordinalText;
    @Bind(R.id.lambdaText) TextView lambdaText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lambda);
        ButterKnife.bind(this);

        TestClass.method(new TestClass.PseudoLambda() {
            @Override
            public void test() {
                ordinalText.setText("Got it " + System.currentTimeMillis());
            }
        });

        TestClass.method(() -> lambdaText.setText("Got it " + System.currentTimeMillis()));

    }
}
