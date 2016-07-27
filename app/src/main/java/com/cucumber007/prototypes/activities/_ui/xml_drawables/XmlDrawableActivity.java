package com.cucumber007.prototypes.activities._ui.xml_drawables;

import android.app.Activity;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.cucumber007.prototypes.R;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class XmlDrawableActivity extends Activity {

    @Bind(R.id.imageView2) ImageView imageView2;
    @Bind(R.id.imageView4) ImageView imageView4;
    @Bind(R.id.imageView3) ImageView imageView3;
    @Bind(R.id.imageView5) ImageView imageView5;
    @Bind(R.id.imageView6) ImageView imageView6;
    @Bind(R.id.imageView7) ImageView imageView7;

    private boolean transition = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_drawable);
        ButterKnife.bind(this);

        LayerDrawable layerDrawable = ((LayerDrawable) imageView2.getDrawable().mutate());
        layerDrawable.setDrawableByLayerId(R.id.layer, getResources().getDrawable(R.mipmap.ic_launcher));

        Observable.interval(0, 500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((lng) -> {
                    Log.d("cutag", "" + lng);
                    LevelListDrawable lld = ((LevelListDrawable) imageView4.getDrawable());
                    lld.setLevel(lld.getLevel() < 100 ? lld.getLevel() + 20 : 0);
                });

        Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((lng) -> {
                    TransitionDrawable td = ((TransitionDrawable) imageView5.getDrawable());
                    if (!transition) {
                        td.startTransition(800);
                        transition = true;
                    } else {
                        td.reverseTransition(200);
                        transition = false;
                    }
                });

    }
}
