package com.cucumber007.prototypes.activities.butterknife;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cucumber007.prototypes.R;

import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ButterknifeActivity extends Activity {

    @BindString(R.string.intro) String title;
    @BindDrawable(R.mipmap.ic_launcher) Drawable graphic;
    @BindColor(R.color.red) int red; // int or ColorStateList field
    @BindDimen(R.dimen.test) int spacer;

    @Bind(R.id.button) Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butteknife);
        ButterKnife.bind(this);

        button.setBackground(graphic);

        ButterKnife.apply(buttons, textAction);
        ButterKnife.apply(buttons, textColorSetter, red);
        ButterKnife.apply(buttons, View.ALPHA, 0.4f);
    }

    @Bind({ R.id.button, R.id.button2, R.id.button3 })
    List<Button> buttons;

    static final ButterKnife.Action<Button> textAction = new ButterKnife.Action<Button>() {
        @Override public void apply(Button view, int index) {
            view.setText("Button "+index);
        }
    };

    static final ButterKnife.Setter<Button, Integer> textColorSetter = new ButterKnife.Setter<Button, Integer>() {
        @Override public void set(Button view, Integer value, int index) {
            view.setTextColor(value);
        }
    };

    @OnClick(R.id.button)
    public void submit(View view) {
        Log.d("cutag", "1");
    }

    @OnClick(R.id.button2)
    public void submit() {
        Log.d("cutag", "2");
    }

    @OnClick(R.id.button3)
    public void sayHi(Button button) {
        button.setText("Hello!");
        Log.d("cutag", "3");
    }

    @OnClick({ R.id.button, R.id.button2, R.id.button3 })
    public void pickDoor(Button button) {
        Log.d("cutag", button.getText().toString());
    }



}
