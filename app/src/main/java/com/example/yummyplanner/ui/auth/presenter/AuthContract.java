package com.example.yummyplanner.ui.auth.presenter;

public interface AuthContract {

    interface View {
        void navigateToHome();
    }

    interface Presenter {

        void continueAsAGeust();
    }
}
