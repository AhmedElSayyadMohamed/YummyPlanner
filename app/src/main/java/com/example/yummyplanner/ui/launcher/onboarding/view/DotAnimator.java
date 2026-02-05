package com.example.yummyplanner.ui.launcher.onboarding.view;

import android.view.View;
import android.view.ViewGroup;

public class DotAnimator {

    private static final long DURATION = 200;
    private static final float SELECTED_SCALE = 1.3f;
    private static final float NORMAL_SCALE = 1f;
    private static final float SELECTED_ALPHA = 1f;
    private static final float NORMAL_ALPHA = 0.6f;

    public static void animate(ViewGroup tabs, int current, int previous) {
        if (tabs == null) return;

        if (previous != -1 && previous < tabs.getChildCount()) {
            View prevDot = tabs.getChildAt(previous);
            resetDot(prevDot);
        }

        if (current < tabs.getChildCount()) {
            View currentDot = tabs.getChildAt(current);
            selectDot(currentDot);
        }
    }

    private static void resetDot(View dot) {
        dot.animate()
                .scaleX(NORMAL_SCALE)
                .scaleY(NORMAL_SCALE)
                .alpha(NORMAL_ALPHA)
                .setDuration(DURATION)
                .start();
    }

    private static void selectDot(View dot) {
        dot.animate()
                .scaleX(SELECTED_SCALE)
                .scaleY(SELECTED_SCALE)
                .alpha(SELECTED_ALPHA)
                .setDuration(DURATION)
                .start();
    }
}
