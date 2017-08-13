package com.cucumber007.prototypes.activities.giver.blur;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class AbstractBlurActivity extends AppCompatActivity {

    protected abstract View getBlurSourceView();
    protected abstract View getBlurTargetView();
    private long time;

    protected void enableBlur(Runnable callback) {
        getBlurTargetView().setVisibility(View.VISIBLE);
        getBlurTargetView().setAlpha(0f);
        BlurBehind.getInstance().execute(getBlurSourceView(), () -> {
            BlurBehind.getInstance().setBackground(getBlurTargetView());
            getBlurTargetView().animate().setDuration(200).alpha(1f);
            callback.run();
        });
    };
    protected void disableBlur() {
        getBlurTargetView().setVisibility(View.GONE);
    };

}
