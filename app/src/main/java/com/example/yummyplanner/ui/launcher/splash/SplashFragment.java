package com.example.yummyplanner.ui.launcher.splash;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieValueCallback;
import com.example.yummyplanner.R;
import com.example.yummyplanner.data.meals.local.appPreferences.AppPreferences;
import com.example.yummyplanner.data.meals.local.appPreferences.AppPreferencesImpl;
import com.example.yummyplanner.data.meals.local.userSession.SessionRepository;
import com.example.yummyplanner.data.meals.local.userSession.SessionRepositoryImpl;
import com.example.yummyplanner.data.meals.local.userSession.UserSessionManager;
import com.example.yummyplanner.databinding.FragmentSplashBinding;
import com.example.yummyplanner.ui.auth.AuthActivity;
import com.example.yummyplanner.ui.layout.LayoutActivity;
import com.example.yummyplanner.ui.launcher.splash.presenter.SplashContract;
import com.example.yummyplanner.ui.launcher.splash.presenter.SplashPresenter;
import com.google.android.material.color.MaterialColors;

public class SplashFragment extends Fragment implements SplashContract.View {

    private FragmentSplashBinding binding;
    private SplashPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppPreferences prefs = new AppPreferencesImpl(getContext().getApplicationContext());
        UserSessionManager sessionManager = new UserSessionManager(prefs);
        SessionRepository sessionRepo = new SessionRepositoryImpl(sessionManager);

        presenter = new SplashPresenter(this, sessionRepo);

        presenter.start();
    }

    @Override
    public void startAnimations() {
        Animation yummyAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.yummy_text_anim);
        Animation plannerAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.planner_text_anim);

        binding.textYummy.startAnimation(yummyAnim);
        binding.textPlanner.startAnimation(plannerAnim);
    }

    @Override
    public void tintLottie() {

        int primaryColor = MaterialColors.getColor(
                binding.splashLottie,
                androidx.appcompat.R.attr.colorPrimary
        );
        binding.splashLottie.addValueCallback(
                new KeyPath("**"),
                LottieProperty.COLOR_FILTER,
                new LottieValueCallback<>(new PorterDuffColorFilter(
                        primaryColor,
                        PorterDuff.Mode.SRC_ATOP
                ))
        );
    }


    @Override
    public void goToOnboarding() {
        NavOptions navOptions = new NavOptions.Builder()
                .setPopUpTo(R.id.splashFragment, true)
                .build();

        NavHostFragment
                .findNavController(this)
                .navigate(
                        R.id.action_splashFragment_to_onboardingFragment,
                        null,
                        navOptions
                );
    }

    @Override
    public void goToHome() {
        Intent intent = new Intent(requireActivity(), LayoutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void goToLogin() {
        Intent intent = new Intent(requireActivity(), AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.destroy();
        binding = null;
    }
}
