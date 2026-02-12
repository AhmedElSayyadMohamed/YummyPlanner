package com.example.yummyplanner.utiles;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

public class ReusableSnackbar {

    private ReusableSnackbar() {}

    public static void show(View rootView, String message, SnackbarConfig config) {
        if (rootView == null || config == null) return;

        Context context = rootView.getContext();

        Snackbar snackbar = Snackbar.make(rootView, "", config.duration);
        Snackbar.SnackbarLayout layout =
                (Snackbar.SnackbarLayout) snackbar.getView();

        layout.setBackgroundColor(Color.TRANSPARENT);
        layout.setPadding(0, 0, 0, 0);

        ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();

        if (layoutParams instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) layoutParams;
            params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            params.setMargins(32, 0, 32, 32);
            layout.setLayoutParams(params);
        } else if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) layoutParams;
            params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            params.setMargins(32, 0, 32, 32);
            layout.setLayoutParams(params);
        }

        View customView;

        if (config.layoutRes != 0) {
            customView = LayoutInflater.from(context)
                    .inflate(config.layoutRes, layout, false);
        } else {
            customView = createDefaultView(context, message, config);
        }

        layout.removeAllViews();
        layout.addView(customView);
        snackbar.show();
    }

    private static View createDefaultView(Context context,
                                          String message,
                                          SnackbarConfig config) {

        CardView cardView = new CardView(context);
        cardView.setRadius(24f);
        cardView.setCardBackgroundColor(config.backgroundColor);
        cardView.setUseCompatPadding(true);

        LinearLayout container = new LinearLayout(context);
        container.setPadding(32, 24, 32, 24);
        container.setOrientation(LinearLayout.HORIZONTAL);
        container.setGravity(Gravity.CENTER_VERTICAL);

        if (config.iconRes != 0) {
            ImageView icon = new ImageView(context);
            icon.setImageResource(config.iconRes);
            icon.setPadding(0, 0, 24, 0);
            container.addView(icon);
        }

        TextView textView = new TextView(context);
        textView.setText(message);
        textView.setTextColor(config.textColor);
        textView.setTextSize(14);

        container.addView(textView);
        cardView.addView(container);

        return cardView;
    }
}
