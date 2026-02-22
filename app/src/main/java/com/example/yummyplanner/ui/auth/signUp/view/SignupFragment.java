package com.example.yummyplanner.ui.auth.signUp.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.yummyplanner.ui.BaseFragment;
import com.example.yummyplanner.R;
import com.example.yummyplanner.data.auth.repository.AuthRepositoryImpl;
import com.example.yummyplanner.databinding.FragmentSignupBinding;
import com.example.yummyplanner.ui.auth.signUp.presenter.SignUpContract;
import com.example.yummyplanner.ui.auth.signUp.presenter.SignUpPresenter;
import com.example.yummyplanner.utiles.Constants;
import com.example.yummyplanner.utiles.LogsConstants;
import com.google.android.material.appbar.MaterialToolbar;


public class SignupFragment extends BaseFragment implements SignUpContract.View {

    private FragmentSignupBinding binding;
    private SignUpContract.Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SignUpPresenter(this,getContext().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        MaterialToolbar toolbar = binding.toolbar;

        presenter.attachView(this);

        setupClearErrorOnTyping(binding.tilUsername, binding.etUsername);
        setupClearErrorOnTyping(binding.tilEmail, binding.etEmail);
        setupClearErrorOnTyping(binding.tilPassword, binding.etPassword);
        setupClearErrorOnTyping(binding.tilConfirmPassword, binding.etConfirmPassword);

        toolbar.setNavigationOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigateUp();
        });

        binding.btnSignUp.setOnClickListener(v -> {
            String fullName = getText(binding.etUsername);
            String email = getText(binding.etEmail);
            String password = getText(binding.etPassword);
            String confirmPassword = getText(binding.etConfirmPassword);

            presenter.registerUser(fullName, email, password, confirmPassword);

            Log.d("userRegister", "I am click register btn ");
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void navigateToLoginScreen() {
        if (isAdded() && getView() != null && binding != null) {
                Navigation.findNavController(binding.getRoot())
                        .navigate(R.id.action_signup_to_login);
                    }
    }

    @Override
    public void showFullNameError(String message) {
        if (binding == null) return;
        binding.tilUsername.setErrorEnabled(true);
        binding.tilUsername.setError(message);
        binding.etUsername.requestFocus();
    }

    @Override
    public void showEmailError(String message) {
        if (binding == null) return;
        binding.tilEmail.setErrorEnabled(true);
        binding.tilEmail.setError(message);
        binding.etEmail.requestFocus();
    }

    @Override
    public void showPasswordError(String message) {
        if (binding == null) return;
        binding.tilPassword.setErrorEnabled(true);
        binding.tilPassword.setError(message);
        binding.etPassword.requestFocus();
    }

    @Override
    public void showConfirmPasswordError(String message) {
        if (binding == null) return;
        binding.tilConfirmPassword.setErrorEnabled(true);
        binding.tilConfirmPassword.setError(message);
        binding.etConfirmPassword.requestFocus();
    }

    @Override
    public void showErrorMessage(String message) {
        if (binding == null) return;
        Constants.showErrorSnackbar(binding.getRoot(),message);
    }

    @Override
    public void showSuccessMessage(String message) {
        if (binding == null) return;
        Constants.showSuccessSnackbar(binding.getRoot(),message);
    }

    @Override
    public void showLoading() {
        if (binding == null) return;
        hideKeyboard();
        binding.loadingContainer.setAlpha(0f);
        binding.loadingContainer.setVisibility(View.VISIBLE);
        binding.loadingContainer.animate().alpha(1f).setDuration(200);
        binding.btnSignUp.setEnabled(false);
    }

    @Override
    public void hideLoading() {
        if (binding == null) return;
        binding.loadingContainer.animate()
                .alpha(0f)
                .setDuration(200)
                .withEndAction(() -> {
                    if (binding != null) {
                        binding.loadingContainer.setVisibility(View.GONE);
                    }
                });
        binding.btnSignUp.setEnabled(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}