package com.example.yummyplanner.ui.launcher.splash.presenter;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.example.yummyplanner.data.repository.MealRepository;
import com.example.yummyplanner.data.repository.MealRepositoryImpl;
import com.example.yummyplanner.ui.launcher.splash.SplashView;

public class splashPresenter implements SplashContract.Presener{

    private Handler handler;
    private static final int SPLASH_DURATION = 5000;

    private SplashContract.View view;
    private final MealRepository mealRepository;

    public splashPresenter(SplashContract.View view, Application application) {
        this.view = view ;
        this.mealRepository = new MealRepositoryImpl(application) ;
        this.handler = new Handler(Looper.getMainLooper());
    }

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

        if (!mealRepository.hasSeenOnboarding()) {
            view.openOnboarding();
            return;
        }

        if (mealRepository.isGuestUser()) {
            view.openHome();
        } else {
            view.openAuth();
        }
    }


    public void destroy() {
        handler.removeCallbacksAndMessages(null);
        view = null;
    }

}
