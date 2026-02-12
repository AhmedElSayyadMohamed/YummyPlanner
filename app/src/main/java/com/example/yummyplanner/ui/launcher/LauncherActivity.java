package com.example.yummyplanner.ui.launcher;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.yummyplanner.R;
import com.example.yummyplanner.utiles.NetworkUtils;
import com.google.android.material.snackbar.Snackbar;

public class LauncherActivity extends AppCompatActivity {

    private Snackbar noInternetSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_launcher);
        
        View mainView = findViewById(R.id.main);
        
        ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Network Callback
        NetworkUtils.registerNetworkCallback(this);

        // Observe Network State
        NetworkUtils.isNetworkAvailable().observe(this, isAvailable -> {
            if (!isAvailable) {
                showNoInternetSnackbar(mainView);
            } else {
                dismissNoInternetSnackbar();
            }
        });
    }

    private void showNoInternetSnackbar(View view) {
        if (noInternetSnackbar == null) {
            noInternetSnackbar = Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                    .setBackgroundTint(Color.RED)
                    .setTextColor(Color.WHITE);
            noInternetSnackbar.getView().setAlpha(0.9f);
        }
        if (!noInternetSnackbar.isShown()) {
            noInternetSnackbar.show();
        }
    }

    private void dismissNoInternetSnackbar() {
        if (noInternetSnackbar != null && noInternetSnackbar.isShown()) {
            noInternetSnackbar.dismiss();
            Snackbar.make(findViewById(R.id.main), "Back Online", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.GREEN)
                    .setTextColor(Color.WHITE)
                    .show();
        }
    }
}
