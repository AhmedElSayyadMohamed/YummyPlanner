package com.example.yummyplanner.ui.launcher.onboarding.presenter;

import android.content.Context;

import com.example.yummyplanner.ui.launcher.onboarding.model.OnboardingItem;
import com.example.yummyplanner.ui.launcher.onboarding.repository.OnboardingRepository;

import java.util.List;

public class OnboardingPresenter implements OnboardingContract.Presenter {

    private OnboardingRepository onboardingRepository;
    private OnboardingContract.View view;
    private int totalPages;
    private int currentPage;
    private final List<OnboardingItem> onboardingItems;

    public OnboardingPresenter(
            OnboardingContract.View view,
            Context context
            ) {
        this.view = view;
        this.onboardingRepository = new OnboardingRepository();
        this.onboardingItems = onboardingRepository.getOnboardingItems();
        this.totalPages = this.onboardingItems.size();
        this.currentPage = 0;
    }

    public List<OnboardingItem> loadItems() {
        return onboardingItems;
    }

    @Override
    public void onPageChanged(int position) {
        boolean isLastPage = position == totalPages - 1;

        view.showSkip(!isLastPage);

        if (isLastPage) {
            view.setNextButtonText("Get Started");
        } else {
            view.setNextButtonText("Next");
        }
    }

    @Override
    public void onNextClicked(int currentPage) {
        if (currentPage < totalPages - 1) {
            view.goToNextPage();
        } else {

            view.navigateToAuthActivity();
        }
    }

    @Override
    public void onSkipClicked() {

        view.navigateToAuthActivity();
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
