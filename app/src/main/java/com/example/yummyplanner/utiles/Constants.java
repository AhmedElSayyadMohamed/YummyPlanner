package com.example.yummyplanner.utiles;

import android.graphics.Color;
import android.view.View;

import com.example.yummyplanner.R;
import com.google.android.material.snackbar.Snackbar;

public class Constants {

    public static void showSuccessSnackbar(View view, String message) {
        SnackbarConfig config = new SnackbarConfig.Builder()
                .backgroundColor(Color.parseColor("#388E3C"))
                .icon(R.drawable.ic_check)
                .duration(Snackbar.LENGTH_SHORT)
                .build();
        ReusableSnackbar.show(view, message, config);
    }

    public static void showErrorSnackbar(View view, String message) {
        SnackbarConfig config = new SnackbarConfig.Builder()
                .backgroundColor(Color.parseColor("#D32F2F"))
                .icon(R.drawable.ic_error)
                .build();
        ReusableSnackbar.show(view, message, config);
    }
}
