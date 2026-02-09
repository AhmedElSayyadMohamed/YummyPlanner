package com.example.yummyplanner.utiles;

import android.graphics.Color;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarConfig {

    public int backgroundColor;
    public int textColor;
    public int duration;
    public int iconRes;
    public int layoutRes;

    public SnackbarConfig(Builder builder) {
        this.backgroundColor = builder.backgroundColor;
        this.textColor = builder.textColor;
        this.duration = builder.duration;
        this.iconRes = builder.iconRes;
        this.layoutRes = builder.layoutRes;
    }

    public static class Builder {
        private int backgroundColor = Color.DKGRAY;
        private int textColor = Color.WHITE;
        private int duration = Snackbar.LENGTH_LONG;
        private int iconRes = 0;
        private int layoutRes = 0;

        public Builder backgroundColor(int color) {
            this.backgroundColor = color;
            return this;
        }

        public Builder textColor(int color) {
            this.textColor = color;
            return this;
        }

        public Builder icon(@DrawableRes int res) {
            this.iconRes = res;
            return this;
        }

        public Builder layout(@LayoutRes int res) {
            this.layoutRes = res;
            return this;
        }

        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public SnackbarConfig build() {
            return new SnackbarConfig(this);
        }
    }
}
