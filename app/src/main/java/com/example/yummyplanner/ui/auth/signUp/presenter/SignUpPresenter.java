package com.example.yummyplanner.ui.auth.signUp.presenter;

import android.util.Log;

import com.example.yummyplanner.data.auth.model.User;
import com.example.yummyplanner.data.auth.repository.AuthRepository;
import com.example.yummyplanner.data.auth.repository.AuthRepositoryImpl;
import com.example.yummyplanner.data.auth.repository.AuthResultCallback;
import com.example.yummyplanner.data.meals.cloud.CloudRemoteDataSource;
import com.example.yummyplanner.utiles.EmailAndPasswordValidation;
import com.example.yummyplanner.utiles.LogsConstants;

public class SignUpPresenter implements SignUpContract.Presenter{

    private SignUpContract.View view ;
    private final AuthRepository authRepo;

    public SignUpPresenter(SignUpContract.View view) {
        this.view = view;
        this.authRepo = AuthRepositoryImpl.getInstance();
    }

    @Override
    public void attachView(SignUpContract.View view) {
        this.view = view;
    }

    @Override
    public void registerUser(String fullName, String email, String password, String confirmPassword) {
        if (EmailAndPasswordValidation.isEmpty(fullName)) {
            if (view != null) view.showFullNameError("Full name is required");
            return;
        }

        if (EmailAndPasswordValidation.isEmpty(email)) {
            if (view != null) view.showEmailError("Email is required");
            return;
        }

        if (EmailAndPasswordValidation.isInValidEmail(email)) {
            if (view != null) view.showEmailError("Invalid email address format");
            return;
        }

        if (EmailAndPasswordValidation.isEmpty(password)) {
            if (view != null) view.showPasswordError("Password is required");
            return;
        }

        if (!EmailAndPasswordValidation.isValidPassword(password)) {
            if (view != null) {
                view.showPasswordError("Password is too weak. Must contain:\n" +
                        "- At least 8 characters\n" +
                        "- One uppercase letter (A-Z)\n" +
                        "- One number (0-9)\n" +
                        "- One special char (@#$%^&+=)");
            }
            return;
        }

        if (!password.equals(confirmPassword)) {
            if (view != null) view.showConfirmPasswordError("Passwords do not match");
            return;
        }

        if (view != null) view.showLoading();
        User user = new User(fullName, email);
        user.setPassword(password);
        Log.d("userRegister", "registerUser in presenter ");

        authRepo.registerWithEmailAndPassword(user,new AuthResultCallback() {
            @Override
            public void onSuccess(User user) {
                if (view == null) return;
                Log.d(LogsConstants.userRegister, "success registerUser in presenter ");

                view.hideLoading();
                view.showSuccessMessage("User registered successfully");
                view.navigateToLoginScreen();
            }

            @Override
            public void onError(String message) {
                if (view == null) return;
                Log.d(LogsConstants.userRegister, "faild registerUser in presenter ");

                view.hideLoading();
                view.showErrorMessage(message != null ? message : "Registration failed");
            }
        });
    }


    @Override
    public void onLoginClicked() {
        if (view != null) view.navigateToLoginScreen();

    }

    @Override
    public void detachView() {
        view = null;

    }
}
