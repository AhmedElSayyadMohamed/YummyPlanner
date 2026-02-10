package com.example.yummyplanner.ui.layout;

import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.yummyplanner.R;
import com.example.yummyplanner.databinding.ActivityLayoutBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


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


        BottomNavigationView bottomNav = binding.bottomNavigation;

        navController.addOnDestinationChangedListener(
                (controller, destination, arguments) -> {

                    if (destination.getId() == R.id.mealDetailsFragment) {
                        bottomNav.animate()
                                .translationY(bottomNav.getHeight())
                                .setDuration(200);
                        bottomNav.setVisibility(View.GONE);

                    } else {
                        bottomNav.setVisibility(View.VISIBLE);
                        bottomNav.animate()
                                .translationY(0)
                                .setDuration(200);
                    }
                }
        );
    }
}