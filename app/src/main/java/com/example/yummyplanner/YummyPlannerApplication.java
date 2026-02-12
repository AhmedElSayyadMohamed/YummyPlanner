package com.example.yummyplanner;

import android.app.Application;
import android.util.Log;

import io.reactivex.rxjava3.exceptions.UndeliverableException;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

public class YummyPlannerApplication extends Application {
    private static final String TAG = "YummyPlannerApp";

    @Override
    public void onCreate() {
        super.onCreate();
        setupRxJavaErrorHandler();
    }

    private void setupRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler(e -> {
            if (e instanceof UndeliverableException) {
                e = e.getCause();
            }
            if (e == null) {
                return;
            }
            Log.e(TAG, "Undeliverable exception received, not further propagating", e);
        });
    }
}
