package com.cucumber007.prototypes.activities.giver;


import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.cucumber007.prototypes.reusables.logging.LogUtil;

public class ExpandAnimationUtil {

    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static ValueAnimator createAnimator(View view,
                                               final int from, final int to, final long duration, final TimeInterpolator interpolator, Runnable endCallback) {
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to);
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.addUpdateListener(animator -> {
            LogUtil.logDebug("", (int) animator.getAnimatedValue());
            view.getLayoutParams().height = (int) animator.getAnimatedValue();
            view.requestLayout();
        });
        valueAnimator.addListener(new AbstractSimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                endCallback.run();
            }
        });
        return valueAnimator;
    }

    public static ValueAnimator createAnimator(View view,
                                               final int from, final int to, final long duration, final TimeInterpolator interpolator) {
       return createAnimator(view, from, to, duration, interpolator, ()->{});
    }

}
