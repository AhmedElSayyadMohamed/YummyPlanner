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
import com.example.yummyplanner.databinding.FragmentSettingBinding;
import com.example.yummyplanner.ui.auth.AuthActivity;


public class SettingFragment extends Fragment implements com.example.yummyplanner.ui.setting.SettingContract.View {

    private FragmentSettingBinding binding;
    private SettingContract.Presenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSettingBinding.inflate(inflater, container, false);
        presenter = new com.example.yummyplanner.ui.setting.SettingPresenter(requireContext(), this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        presenter.loadUser();
        presenter.loadStats();

        binding.btnLogout.setOnClickListener(v -> showLogoutConfirmationDialog());
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout", (dialog, which) -> presenter.logout())
                .setNegativeButton("Cancel", null)
                .show();
    }


    @Override
    public void showUser(User user) {
        binding.tvProfileName.setText(
                user.getName() != null && !user.getName().isEmpty()
                        ? user.getName() : "Guest"
        );

        binding.tvProfileEmail.setText(user.getEmail());

        if (user.getAvatarUrl() != null && !user.getAvatarUrl().isEmpty()) {
            Glide.with(this)
                    .load(user.getAvatarUrl())
                    .placeholder(R.drawable.profile)
                    .into(binding.profileImage);
        }
    }

    @Override
    public void showGuest() {
        binding.tvProfileName.setText("Guest");
        binding.tvProfileEmail.setText("");
    }

    @Override
    public void showFavoriteCount(int count) {
        binding.tvFavCount.setText(String.valueOf(count));
    }

    @Override
    public void showPlanCount(int count) {
        binding.tvPlanCount.setText(String.valueOf(count));
    }

    @Override
    public void navigateToAuth() {
        Intent intent = new Intent(requireContext(), AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showError(String message) {}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
        binding = null;
    }
}
