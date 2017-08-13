package com.cucumber007.prototypes.activities.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.cucumber007.prototypes.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MvpActivity extends Activity implements TestPresenter.TestView {

    //if list, one presenter for all items (MessagePresenter(messageId))

    @Bind(R.id.textView5) TextView textView5;

    private TestPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        ButterKnife.bind(this);

        presenter = new TestPresenter(this);
    }

    @Override
    public void displayData(String data) {
        textView5.setText(data);
    }


    @OnClick(R.id.textView5)
    public void onClick() {
        presenter.notifyClick();
    }
}
