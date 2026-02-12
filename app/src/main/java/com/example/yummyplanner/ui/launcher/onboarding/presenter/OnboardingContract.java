package com.example.yummyplanner.ui.launcher.onboarding.presenter;

import com.example.yummyplanner.ui.launcher.onboarding.model.OnboardingItem;

import java.util.List;

public interface OnboardingContract {

    interface View {
        void showSkip(boolean show);
        void setNextButtonText(String text);
        void goToNextPage();
        void navigateToAuthActivity();
    }

    interface Presenter{
        List<OnboardingItem> loadItems();
        void onPageChanged(int position);
        void onNextClicked(int currentPage);
        void onSkipClicked();
        void onDestroy();
    }
}
