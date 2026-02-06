package com.example.yummyplanner.ui.auth.presenter;

import com.example.yummyplanner.data.local.userSession.SessionRepository;

public class AuthPresenter implements AuthContract.Presenter{

    private AuthContract.View view;
    private SessionRepository sessionRepo;
    public AuthPresenter(AuthContract.View view,SessionRepository sessionRepo){
        this.view = view ;
        this.sessionRepo = sessionRepo;
    }

    @Override
    public void onLoginClicked(String email, String password) {

    }

    @Override
    public void continueAsAGeust() {
        sessionRepo.enterGuest();
        view.navigateToHome();
    }
}
