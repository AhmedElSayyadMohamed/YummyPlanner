package com.example.yummyplanner.ui.launcher.onboarding.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.yummyplanner.R;
import com.example.yummyplanner.data.local.appPreferences.AppPreferences;
import com.example.yummyplanner.data.local.appPreferences.AppPreferencesImpl;
import com.example.yummyplanner.data.local.userSession.SessionRepository;
import com.example.yummyplanner.data.local.userSession.SessionRepositoryImpl;
import com.example.yummyplanner.data.local.userSession.UserSessionManager;
import com.example.yummyplanner.ui.auth.AuthActivity;
import com.example.yummyplanner.ui.launcher.onboarding.model.OnboardingItem;
import com.example.yummyplanner.ui.launcher.onboarding.presenter.OnboardingContract;
import com.example.yummyplanner.ui.launcher.onboarding.presenter.OnboardingPresenter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;


public class OnboardingFragment extends Fragment  implements OnboardingContract.View {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private MaterialButton btnSkip, btnNext;

    private OnboardingContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_onboarding, container, false);

        // presenter
        AppPreferences appPreferences = new AppPreferencesImpl(requireActivity());
        UserSessionManager userSessionManager = new UserSessionManager(appPreferences);
        SessionRepository sessionRepository = new SessionRepositoryImpl(userSessionManager);
        presenter = new OnboardingPresenter(this, sessionRepository);

        //ui binding
        viewPager = view.findViewById(R.id.onboardingViewPager);
        tabLayout = view.findViewById(R.id.dotsIndicator);
        btnSkip = view.findViewById(R.id.btnSkip);
        btnNext = view.findViewById(R.id.onboardingNavBtn);

        List<OnboardingItem> items = presenter.loadItems();
        viewPager.setAdapter(new OnboardingAdapter(items));

        viewPager.setPageTransformer((page, position) -> {
            float scale = 0.85f + (1 - Math.abs(position)) * 0.15f;
            page.setScaleX(scale);
            page.setScaleY(scale);
            page.setAlpha(0.5f + (1 - Math.abs(position)) * 0.5f);
        });


        // connect dots with tabLayout
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setIcon(R.drawable.dot_indicator)).attach();

        // onPageChange
        viewPager.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {
                    int previous = -1;

                    @Override
                    public void onPageSelected(int position) {
                        animateDot(position, previous);
                        presenter.onPageChanged(position);
                        previous = position;
                    }
                }
        );

        btnNext.setOnClickListener(v -> presenter.onNextClicked(viewPager.getCurrentItem()));
        btnSkip.setOnClickListener(v -> presenter.onSkipClicked());

        tabLayout.post(() -> animateDot(0, -1));

        return view;
    }

    private void animateDot(int current, int previous) {
        if (tabLayout == null) return;

        ViewGroup tabs = (ViewGroup) tabLayout.getChildAt(0);
        if (tabs == null || tabs.getChildCount() == 0) return;

        DotAnimator.animate(tabs, current, previous);
    }

    @Override
    public void showSkip(boolean show) {
        btnSkip.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setNextButtonText(String text) {
        btnNext.setText(text);
    }

    @Override
    public void goToNextPage() {
        viewPager.setCurrentItem(
                viewPager.getCurrentItem() + 1,
                true   // animation
        );
    }

    @Override
    public void navigateToAuthActivity() {
        Intent intent = new Intent(this.getContext(), AuthActivity.class);
        startActivity(intent);
    }
}
