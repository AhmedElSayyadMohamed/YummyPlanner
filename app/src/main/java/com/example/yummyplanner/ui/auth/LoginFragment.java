package com.example.yummyplanner.ui.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yummyplanner.R;
import com.example.yummyplanner.data.local.appPreferences.AppPreferences;
import com.example.yummyplanner.data.local.appPreferences.AppPreferencesImpl;
import com.example.yummyplanner.data.local.userSession.SessionRepository;
import com.example.yummyplanner.data.local.userSession.SessionRepositoryImpl;
import com.example.yummyplanner.data.local.userSession.UserSessionManager;
import com.example.yummyplanner.databinding.FragmentLoginBinding;
import com.example.yummyplanner.ui.auth.presenter.AuthContract;
import com.example.yummyplanner.ui.auth.presenter.AuthPresenter;
import com.example.yummyplanner.ui.home.LayoutActivity;


public class LoginFragment extends Fragment implements AuthContract.View {

    private FragmentLoginBinding loginBinding;
    private AuthPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loginBinding = FragmentLoginBinding.inflate(inflater, container, false);

        AppPreferences prefs = new AppPreferencesImpl(getActivity().getApplication());
        UserSessionManager sessionManager = new UserSessionManager(prefs);
        SessionRepository sessionRepo = new SessionRepositoryImpl(sessionManager);
        presenter = new AuthPresenter(this, sessionRepo);

        loginBinding.createNewAccount.setOnClickListener(onClickCreateNewAccount());

        loginBinding.btnGuest.setOnClickListener(onClickContinueAsAGeust());
        return loginBinding.getRoot();
    }

    private View.OnClickListener onClickCreateNewAccount() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_login_to_signup);
            }
        };
    }


    private View.OnClickListener onClickContinueAsAGeust() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.continueAsAGeust();
                navigateToHome();
            }
        };
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }



    @Override
    public void showError(String msg) {

    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(getContext(), LayoutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}