package com.example.yummyplanner.ui.auth.login.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.navigation.Navigation;

import com.example.yummyplanner.ui.BaseFragment;
import com.example.yummyplanner.R;
import com.example.yummyplanner.data.auth.social.GoogleAuthHelper;
import com.example.yummyplanner.data.auth.social.SocialAuthCallback;
import com.example.yummyplanner.data.meals.local.userSession.SessionRepository;
import com.example.yummyplanner.data.meals.local.userSession.SessionRepositoryImpl;
import com.example.yummyplanner.databinding.FragmentLoginBinding;
import com.example.yummyplanner.ui.layout.LayoutActivity;
import com.example.yummyplanner.utiles.Constants;

public class LoginFragment extends BaseFragment implements LoginContract.View {

    private FragmentLoginBinding binding;
    private LoginPresenter presenter;
    private GoogleAuthHelper googleAuthHelper;

    private final SocialAuthCallback socialAuthCallback = new SocialAuthCallback() {
        @Override
        public void onSuccess(String idToken) {
            requireActivity().runOnUiThread(() ->
                    presenter.firebaseAuthWithGoogle(idToken)
            );
        }

        @Override
        public void onError(String message) {
            requireActivity().runOnUiThread(() -> {
                hideLoading();
                showErrorMessage(message);
            });
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new LoginPresenter(this,getContext().getApplicationContext());
        googleAuthHelper = new GoogleAuthHelper(requireActivity(), socialAuthCallback);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        setupListeners();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attachView(this);
    }

    private void setupListeners() {

        binding.loginBtn.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();
            Log.d("LOGIN", "Login button clicked");

            presenter.onLoginClicked(email, password);
        });

        binding.createNewAccount.setOnClickListener(v ->
                presenter.onCreateAccountClicked()
        );

        binding.btnGoogle.setOnClickListener(v ->{
            Log.d("LOGIN", "Google button clicked");
            presenter.onGoogleLoginClicked();
        }
        );

        binding.btnFacebook.setOnClickListener(v ->{
                    Constants.showSuccessSnackbar(binding.getRoot(), "Facebook Login coming soon!");
                }
        );

        binding.btnGuest.setOnClickListener(v->{

            presenter.continueAsAGeust();

        });
    }


    @Override
    public void showLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.loginBtn.setEnabled(false);
    }

    @Override
    public void hideLoading() {
        binding.progressBar.setVisibility(View.GONE);
        binding.loginBtn.setEnabled(true);
    }

    @Override
    public void showEmailError(String message) {
        binding.tilEmail.setError(message);
        binding.tilEmail.requestFocus();
    }

    @Override
    public void showPasswordError(String message) {
        binding.tilPassword.setError(message);
        binding.tilPassword.requestFocus();
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(requireContext(), LayoutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void navigateToSignup() {
        if (isAdded()) {
            Navigation.findNavController(binding.getRoot())
                    .navigate(R.id.action_login_to_signup);
        }
    }

    @Override
    public void launchGoogleLogin() {
        showLoading();
        Log.d("LOGIN", "We are in LoginView and will call launchSignIn() from googleAuthHelper");

        googleAuthHelper.launchSignIn();
    }

    @Override
    public void showErrorMessage(String message) {
        Constants.showErrorSnackbar(binding.getRoot(), message);
    }

    @Override
    public void showSuccessMessage(String message) {

        Constants.showSuccessSnackbar(binding.getRoot(), message);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        binding = null;
    }
}
