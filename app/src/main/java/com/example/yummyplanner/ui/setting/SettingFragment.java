package com.example.yummyplanner.ui.setting;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.yummyplanner.R;
import com.example.yummyplanner.data.auth.model.User;
import com.example.yummyplanner.data.auth.repository.AuthRepositoryImpl;
import com.example.yummyplanner.data.meals.local.userSession.UserSessionManager;
import com.example.yummyplanner.data.meals.repository.MealRepositoryImpl;
import com.example.yummyplanner.databinding.FragmentSettingBinding;
import com.example.yummyplanner.ui.auth.AuthActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class SettingFragment extends Fragment {


    private FragmentSettingBinding binding;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        User currentUser = UserSessionManager.getInstance(requireContext()).getUser();

        if (currentUser != null) {
            binding.tvProfileName.setText(currentUser.getName() != null && !currentUser.getName().isEmpty() ? currentUser.getName() : "Guest");
            binding.tvProfileEmail.setText(currentUser.getEmail());
            if (currentUser.getAvatarUrl() != null && !currentUser.getAvatarUrl().isEmpty()) {
                Glide.with(this).load(currentUser.getAvatarUrl()).placeholder(R.drawable.profile).into(binding.profileImage);
            }
        } else {
            binding.tvProfileName.setText("Guest");
            binding.tvProfileEmail.setText("");
        }

        loadStats();

        binding.btnLogout.setOnClickListener(v -> {
            showLogoutConfirmationDialog();
        });
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout", (dialog, which) -> {
                    performLogout();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void performLogout() {
        disposables.add(
                AuthRepositoryImpl.getInstance().logout()
                        .andThen(MealRepositoryImpl.getInstance(requireContext()).clearAllData())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            UserSessionManager.getInstance(requireContext()).logout();
                            navigateToAuth();
                        }, throwable -> {
                            // Handle error if needed
                        })
        );
    }

    private void loadStats() {
        disposables.add(
                MealRepositoryImpl.getInstance(requireContext()).getAllFavorites()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(favorites -> {
                            binding.tvFavCount.setText(String.valueOf(favorites.size()));
                        }, throwable -> {
                            binding.tvFavCount.setText("0");
                        })
        );

        disposables.add(
                MealRepositoryImpl.getInstance(requireContext()).getAllPlannedMeals()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(plannedMeals -> {
                            binding.tvPlanCount.setText(String.valueOf(plannedMeals.size()));
                        }, throwable -> {
                            binding.tvPlanCount.setText("0");
                        })
        );
    }

    private void navigateToAuth() {
        Intent intent = new Intent(requireContext(), AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposables.clear();
        binding = null;
    }
}
