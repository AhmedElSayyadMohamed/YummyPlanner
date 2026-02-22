package com.example.yummyplanner.ui.auth.signUp.presenter;

public interface SignUpContract {
    interface View {
        void navigateToLoginScreen();
        void showFullNameError(String message);
        void showEmailError(String message);
        void showPasswordError(String message);
        void showConfirmPasswordError(String message);
        void showErrorMessage(String message);
        void showSuccessMessage(String message);
        void showLoading();
        void hideLoading();
    }

    interface Presenter {
        void attachView(SignUpContract.View view);
        void registerUser(String fullName, String email, String password, String confirmPassword);
        void detachView();
    }
}
