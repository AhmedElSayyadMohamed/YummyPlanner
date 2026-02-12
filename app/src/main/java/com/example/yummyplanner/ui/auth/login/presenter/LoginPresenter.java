package com.example.yummyplanner.ui.auth.login.presenter;


import com.example.yummyplanner.data.auth.repository.AuthRepository;
import com.example.yummyplanner.data.auth.repository.AuthRepositoryImpl;
import com.example.yummyplanner.data.meals.local.userSession.SessionRepository;
import com.example.yummyplanner.utiles.EmailAndPasswordValidation;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private final SessionRepository sessionRepo;
    private final AuthRepository authRepo;

    private final CompositeDisposable disposables = new CompositeDisposable();

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

        disposables.add(
                authRepo.loginWithEmailAndPassword(email, password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                user -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.showSuccessMessage("Login Successful");
                                        sessionRepo.login();
                                        sessionRepo.saveUser(user);
                                        view.navigateToHome();
                                    }
                                },
                                throwable -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.showErrorMessage(throwable.getMessage() != null ?
                                                throwable.getMessage() : "Login failed");
                                    }
                                }
                        )
        );
    }

    @Override
    public void firebaseAuthWithGoogle(String idToken) {
        if (view != null) view.showLoading();

        disposables.add(
                authRepo.loginWithGoogle(idToken)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                user -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.showSuccessMessage("Google Sign-In Successful");
                                        sessionRepo.login();
                                        sessionRepo.saveUser(user);
                                        view.navigateToHome();
                                    }
                                },
                                throwable -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.showErrorMessage(throwable.getMessage() != null ?
                                                throwable.getMessage() : "Google Auth Failed");
                                    }
                                }
                        )
        );
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
        if (view != null) view.launchGoogleLogin();
    }

    @Override
    public void detachView() {
        disposables.clear();
        view = null;
    }
}