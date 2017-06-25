package com.cucumber007.prototypes.activities.giver;


import android.animation.Animator;
import android.view.View;

public class FadeAnimationUtil {

    public static void showWithFade(View view, int duration) {
        showWithFade(view, duration, ()->{});
    };

    public static void showWithFade(View view) {
        showWithFade(view, 300, ()->{});
    };

    public static void showWithFade(View view, int duration, Runnable callback) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0f);
        view.animate().alpha(1f).setDuration(duration).setListener(new AbstractSimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                view.setAlpha(1f);
                callback.run();
            }
        });
    };

    public static void hideWithFade(View view) {
        hideWithFade(view, 200, ()->{});
    };

    public static void hideWithFade(View view, int duration) {
        hideWithFade(view, duration, ()->{});
    };

    public static void hideWithFade(View view, int duration, Runnable callback) {
        view.setAlpha(1f);
        view.animate().alpha(0f).setDuration(duration).setListener(new AbstractSimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.GONE);
                callback.run();
            }
        });
    };

}
