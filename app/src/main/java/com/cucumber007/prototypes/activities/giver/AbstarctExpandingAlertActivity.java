package com.cucumber007.prototypes.activities.giver;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.cucumber007.prototypes.R;
import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class AbstarctExpandingAlertActivity extends Activity {

    ExpandableRelativeLayout content;

    private Activity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abstarct_expanding_alert);

        content = ButterKnife.findById(this, R.id.content);
        LayoutInflater.from(this).inflate(getContentView(), content);
        ButterKnife.bind(this);

        content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                content.expand();
                content.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }

    public ViewGroup getContent() {
        return content;
    }

    @LayoutRes
    protected abstract int getContentView();

    protected void expand() {
        content.expand();
    }

    protected void collapse() {
        content.collapse();
    }

    protected void onClickOutside() {
        onBackPressed();
    }

    protected void finishWithResult(int result) {
        closeAnimation(()-> {
            setResult(result);
            finish();
        });
    }

    private void closeAnimation(Runnable runnable) {
        content.setListener(new ExpandableLayoutListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
            }

            @Override
            public void onPreOpen() {
            }

            @Override
            public void onPreClose() {
            }

            @Override
            public void onOpened() {
            }

            @Override
            public void onClosed() {
                runnable.run();
            }
        });
        content.collapse();
    }

    @Override
    public void onBackPressed() {
        finishWithResult(RESULT_CANCELED);
    }

    @OnClick(R.id.outside)
    public void onClick() {
        onClickOutside();
    }
}
