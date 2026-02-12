package com.example.yummyplanner.ui.layout;

import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.yummyplanner.R;
import com.example.yummyplanner.databinding.ActivityLayoutBinding;
import com.example.yummyplanner.utiles.NetworkUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class LayoutActivity extends AppCompatActivity {

    private ActivityLayoutBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment);

        navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(
                binding.bottomNavigation,
                navController
        );

        NetworkUtils.registerNetworkCallback(this);
        handleNetworkChanges();

        BottomNavigationView bottomNav = binding.bottomNavigation;

        navController.addOnDestinationChangedListener(
                (controller, destination, arguments) -> {
                    updateNoInternetVisibility(NetworkUtils.isNetworkAvailable().getValue() != null ? NetworkUtils.isNetworkAvailable().getValue() : true);

                    if (destination.getId() == R.id.mealDetailsFragment || destination.getId() == R.id.searchFragment) {
                        bottomNav.animate()
                                .translationY(bottomNav.getHeight())
                                .setDuration(200)
                                .withEndAction(() -> bottomNav.setVisibility(View.GONE));

                    } else {
                        bottomNav.setVisibility(View.VISIBLE);
                        bottomNav.animate()
                                .translationY(0)
                                .setDuration(200);
                    }
                }
        );
    }

    private void handleNetworkChanges() {
        NetworkUtils.isNetworkAvailable().observe(this, isAvailable -> {
            updateNoInternetVisibility(isAvailable);
        });
    }

    private void updateNoInternetVisibility(boolean isAvailable) {
        int destinationId = navController.getCurrentDestination().getId();
        
        // Show offline layout only if not on Favorites or Planner
        if (!isAvailable && destinationId != R.id.favoritesFragment && destinationId != R.id.plannerFragment) {
            binding.noInternetView.noInternetLayout.setVisibility(View.VISIBLE);
            binding.navHostFragment.setVisibility(View.GONE);
        } else {
            binding.noInternetView.noInternetLayout.setVisibility(View.GONE);
            binding.navHostFragment.setVisibility(View.VISIBLE);
        }
    }
}
