package com.example.yummyplanner.ui.launcher.splash.presenter;

public interface SplashContract {

    interface View {

        void startAnimations();
        void tintLottie();

        void goToOnboarding();
        void goToHome();
        void goToLogin();
    }

    interface Presenter {
        void start();
        void decideNextScreen();
        void destroy();
    }
}
