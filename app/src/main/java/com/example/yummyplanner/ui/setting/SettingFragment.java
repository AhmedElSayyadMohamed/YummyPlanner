package com.example.yummyplanner.ui.setting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.yummyplanner.R;
import com.example.yummyplanner.data.auth.model.User;
import com.example.yummyplanner.data.meals.local.userSession.UserSessionManager;
import com.example.yummyplanner.databinding.FragmentSettingBinding;


public class SettingFragment extends Fragment {


    private FragmentSettingBinding binding;

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
            binding.tvProfileName.setText(currentUser.getName());
            binding.tvProfileEmail.setText(currentUser.getEmail());
            if (currentUser.getAvatarUrl() != null && !currentUser.getAvatarUrl().isEmpty()) {
                Glide.with(this).load(currentUser.getAvatarUrl()).placeholder(R.drawable.profile).into(binding.profileImage);
            }
        }

        binding.btnLogout.setOnClickListener(v -> {
            UserSessionManager.getInstance(requireContext()).logout();

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
