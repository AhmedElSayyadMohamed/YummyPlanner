package com.example.yummyplanner.ui.auth.login.presenter;

import android.util.Log;

import com.example.yummyplanner.data.auth.model.User;
import com.example.yummyplanner.data.auth.repository.AuthRepository;
import com.example.yummyplanner.data.auth.repository.AuthRepositoryImpl;
import com.example.yummyplanner.data.auth.repository.AuthResultCallback;
import com.example.yummyplanner.data.local.userSession.SessionRepository;
import com.example.yummyplanner.utiles.EmailAndPasswordValidation;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private SessionRepository sessionRepo;
    private AuthRepository authRepo;

    public LoginPresenter(LoginContract.View view, SessionRepository sessionRepo){
        this.view = view ;
        this.sessionRepo = sessionRepo;
        this.authRepo = AuthRepositoryImpl.getInstance();
    }

    @Override
    public void attachView(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void onLoginClicked(String email, String password) {

         if(EmailAndPasswordValidation.isEmpty(email)){
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

        if (view != null) view.showLoading();

        Log.d("LOGIN", "call  authRepo.loginWithEmailAndPassword ");
        authRepo.loginWithEmailAndPassword(email, password, new AuthResultCallback() {
            @Override
            public void onSuccess(User user) {
                Log.d("LOGIN", "Success  authRepo.loginWithEmailAndPassword ");

                if (view == null) return;
                view.hideLoading();
                view.showSuccessMessage("Login Successful");
                view.navigateToHome();
            }

            @Override
            public void onError(String message) {
                Log.d("LOGIN", "FAil  authRepo.loginWithEmailAndPassword ");

                if (view == null) return;
                view.hideLoading();
                view.showErrorMessage(message != null ? message : "Login failed");
            }
        });
    }

    @Override
    public void continueAsAGeust() {
        if (view == null) return;
        sessionRepo.enterGuest();
        view.showSuccessMessage("You Are Logged In As Guest");
        view.navigateToHome();
    }

    @Override
    public void onCreateAccountClicked() {
        if (view != null) view.navigateToSignup();
    }

    @Override
    public void onFacebookLoginClicked() {
        if (view != null) view.showErrorMessage("Facebook Login coming soon!");

    }

    @Override
    public void onGoogleLoginClicked() {
        Log.d("LOGIN", "We are in LoginPresenter and will fire launchGoogleLogin that in imp in view");

        if (view != null) view.launchGoogleLogin();
    }

    @Override
    public void firebaseAuthWithGoogle(String idToken) {
        if (view != null) view.showLoading();

        authRepo.loginWithGoogle(idToken, new AuthResultCallback() {
            @Override
            public void onSuccess(User user) {
                if (view == null) return;
                view.hideLoading();
                view.showSuccessMessage("Google Sign-In Successful");
                view.navigateToHome();
            }

            @Override
            public void onError(String message) {
                if (view == null) return;
                view.hideLoading();
                view.showErrorMessage(message != null ? message : "Google Auth Failed");
            }
        });
    }

    @Override
    public void detachView() {
        view = null;
    }
}
