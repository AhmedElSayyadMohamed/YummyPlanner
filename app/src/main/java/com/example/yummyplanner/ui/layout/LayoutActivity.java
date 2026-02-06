package com.example.yummyplanner.ui.layout;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.yummyplanner.R;
import com.example.yummyplanner.databinding.ActivityLayoutBinding;


public class LayoutActivity extends AppCompatActivity {

    private ActivityLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  inflate binding
        binding = ActivityLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //  get NavHostFragment by container id
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment);

        NavController navController = navHostFragment.getNavController();

        //  setup bottom nav
        NavigationUI.setupWithNavController(
                binding.bottomNavigation,
                navController
        );
    }
}