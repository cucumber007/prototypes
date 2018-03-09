package com.cucumber007.prototypes.sandbox.anvil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.cucumber007.prototypes.R;

import trikita.anvil.Anvil;
import trikita.anvil.RenderableView;
import static trikita.anvil.DSL.*;

public class AnvilActivity extends AppCompatActivity {

    //todo https://github.com/zserge/anvil

    public int ticktock = 0;
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(new RenderableView(this) {
            @Override
            public void view() {
                linearLayout(() -> {
                    size(MATCH, MATCH);
                    padding(dip(8));
                    orientation(LinearLayout.VERTICAL);

                    textView(() -> {
                        size(MATCH, WRAP);
                        text("Tick-tock: " + ticktock);
                    });

                    button(() -> {
                        size(MATCH, WRAP);
                        text("Add");
                        // Finish current activity when the button is clicked
                        onClick(v -> {
                            ticktock++;
                        });
                    });

                    button(() -> {
                        size(MATCH, WRAP);
                        text("Close");
                        // Finish current activity when the button is clicked
                        onClick(v -> finish());
                    });

                });
            }
        });
    }
}
