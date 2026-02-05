package com.example.yummyplanner.ui.launcher.splash;

import android.os.Handler;
import android.os.Looper;

public class splashPresenter {

    private SplashView view;
    private Handler handler;
    private static final int SPLASH_DURATION = 5000;

    public splashPresenter(SplashView view) {
        this.view = view;
        this.handler = new Handler(Looper.getMainLooper());
    }

    public void start() {
        if (view == null) return;

        view.startAnimations();
        view.tintLottie();

        handler.postDelayed(() -> {
            if (view != null) {
                view.navigateToOnboarding();
            }
        }, SPLASH_DURATION);
    }

    public void destroy() {
        handler.removeCallbacksAndMessages(null);
        view = null;
    }
}
