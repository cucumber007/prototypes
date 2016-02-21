package com.cucumber007.prototypes.activities.mvc;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cucumber007.prototypes.MyApplication;
import com.cucumber007.prototypes.R;

import java.util.Observable;
import java.util.Observer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MvcActivity extends Activity {

    @Bind(R.id.editText) EditText editText;
    @Bind(R.id.button4) Button button4;
    @Bind(R.id.button5) Button button5;
    @Bind(R.id.textView2) TextView textView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvc);
        ButterKnife.bind(this);

        DataController.getInstance().setStringDataObserver(new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                textView2.setText(((SharedPrefsModel.StringObservable) observable).getData());
            }
        });

    }


    @OnClick(R.id.button4)
    void click() {
        DataController.getInstance().saveData(editText.getText().toString());
    }

    @OnClick(R.id.button5)
    void click1() {
        DataController.getInstance().saveDataAsync(editText.getText().toString(), new IDatabaseModel.IDatabaseCallback() {
            @Override
            public void onFinish() {
                Toast.makeText(MyApplication.getContext(), "Data saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
