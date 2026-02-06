package com.example.yummyplanner.ui.auth.presenter;

public interface AuthContract {

    interface View {
        void showLoading();
        void hideLoading();
        void showError(String msg);
        void navigateToHome();
    }

    interface Presenter {
        void onLoginClicked(String email, String password);
        void continueAsAGeust();
    }
}
