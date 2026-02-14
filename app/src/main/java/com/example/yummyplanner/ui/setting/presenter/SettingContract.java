package com.example.yummyplanner.ui.setting;

import com.example.yummyplanner.data.auth.model.User;

public interface SettingContract {

    interface View {
        void showUser(User user);
        void showGuest();
        void showFavoriteCount(int count);
        void showPlanCount(int count);
        void navigateToAuth();
        void showError(String message);
    }

    interface Presenter {
        void loadUser();
        void loadStats();
        void logout();
        void onDestroy();
    }
}
