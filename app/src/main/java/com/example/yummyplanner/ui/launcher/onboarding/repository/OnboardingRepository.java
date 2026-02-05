package com.example.yummyplanner.ui.launcher.onboarding.repository;

import com.example.yummyplanner.ui.launcher.onboarding.model.OnboardingItem;

import java.util.ArrayList;
import java.util.List;

public class OnboardingRepository {

    public List<OnboardingItem> getOnboardingItems() {

        List<OnboardingItem> items = new ArrayList<>();

        items.add(new OnboardingItem(
                "onboarding_7.json",
                "Plan your meals",
                "Create your weekly meal plan easily"));

        items.add(new OnboardingItem(
                "onboarding_11.json",
                "Save your favorites",
                "Access your favorite meals anytime"
        ));

        items.add(new OnboardingItem(
                "onboarding_6.json",
                "Cook smarter",
                "Step by step cooking instructions"
        ));

        return items;
    }
}
