package com.example.yummyplanner.ui.launcher.splash;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
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
import com.example.yummyplanner.databinding.FragmentSplashBinding;

public class SplashFragment extends Fragment implements SplashView {

    private FragmentSplashBinding binding;
    private splashPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new splashPresenter(this);
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
        binding.splashLottie.addValueCallback(
                new KeyPath("**"),
                LottieProperty.COLOR_FILTER,
                new LottieValueCallback<>(new PorterDuffColorFilter(
                        ContextCompat.getColor(requireContext(), R.color.primary_700),
                        PorterDuff.Mode.SRC_ATOP
                ))
        );
    }

    @Override
    public void navigateToOnboarding() {
        NavHostFragment
                .findNavController(this)
                .navigate(R.id.action_splashFragment_to_onboardingFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.destroy();
        binding = null;
    }
}
