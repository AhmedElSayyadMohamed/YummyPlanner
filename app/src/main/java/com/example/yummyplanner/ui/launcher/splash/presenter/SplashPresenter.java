package com.example.yummyplanner.ui.launcher.splash.presenter;

import android.os.Handler;
import android.os.Looper;

import com.example.yummyplanner.data.local.userSession.SessionRepository;

public class SplashPresenter implements SplashContract.Presenter {

    private Handler handler;
    private static final int SPLASH_DURATION = 5000;

    private SplashContract.View view;

    private SessionRepository sessionRepo;

    public SplashPresenter(SplashContract.View view,SessionRepository sessionRepo) {
        this.view = view ;
        this.sessionRepo = sessionRepo;
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void start() {
        if (view == null) return;

        view.startAnimations();
        view.tintLottie();

        handler.postDelayed(() -> {
            if (view != null) {
                decideNextScreen();
            }
        }, SPLASH_DURATION);
    }


    @Override
    public void decideNextScreen() {
        if (!sessionRepo.isOnboardingCompleted()) {
            view.goToOnboarding();
            return;
        }

        if (sessionRepo.isLoggedIn() || sessionRepo.isGuest()) {
            view.goToHome();
        } else {
            view.goToLogin();
        }
    }

    @Override
    public void destroy() {
        handler.removeCallbacksAndMessages(null);
        view = null;
    }
}
