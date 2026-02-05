package com.example.yummyplanner.ui.launcher.splash.presenter;

public interface SplashContract {

    interface View {

        void startAnimations();
        void tintLottie();

        void openOnboarding();
        void openHome();
        void openAuth();
    }

    interface Presener {
        void decideNextScreen();
    }
}
