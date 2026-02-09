package com.example.yummyplanner.ui.auth.login.presenter;

public interface LoginContract {

    interface View {
        void showLoading();
        void hideLoading();

        void showEmailError(String message);
        void showPasswordError(String message);

        void navigateToHome();
        void navigateToSignup();
        void launchGoogleLogin();
        void showErrorMessage(String msg);
        void showSuccessMessage(String message);

    }

    interface Presenter {
        void onLoginClicked(String email, String password);
        void continueAsAGeust();
        void attachView(LoginContract.View view);

        void onCreateAccountClicked();
        void onFacebookLoginClicked();
        void onGoogleLoginClicked();
        void firebaseAuthWithGoogle(String idToken);
        void detachView();

    }
}
