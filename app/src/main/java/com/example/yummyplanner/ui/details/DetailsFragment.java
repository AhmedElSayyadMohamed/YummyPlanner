package com.example.yummyplanner.ui.details;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.yummyplanner.R;
import com.example.yummyplanner.data.meals.local.userSession.UserSessionManager;
import com.example.yummyplanner.data.meals.repository.MealRepositoryImpl;
import com.example.yummyplanner.databinding.FragmentDetailsBinding;
import com.example.yummyplanner.data.meals.model.MealdetailsItemModel;
import com.example.yummyplanner.data.meals.model.response.Ingredient;
import com.example.yummyplanner.ui.auth.AuthActivity;
import com.example.yummyplanner.ui.details.presenter.MealDetailsContract;
import com.example.yummyplanner.ui.details.presenter.MealDetailsPresenter;
import com.example.yummyplanner.utiles.Constants;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Calendar;
import java.util.List;

public class DetailsFragment extends Fragment implements MealDetailsContract.View, IngredientsAdapter.OnIngredientClickListener {

    private FragmentDetailsBinding binding;
    private MealDetailsContract.Presenter presenter;
    private IngredientsAdapter ingredientsAdapter;
    private String videoId;
    private MealdetailsItemModel meal;
    private boolean isFavorite = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DetailsFragmentArgs args = DetailsFragmentArgs.fromBundle(getArguments());
        String mealId = args.getMealId();

        presenter = new MealDetailsPresenter(this, MealRepositoryImpl.getInstance(requireContext()));
        presenter.getMealDetails(mealId);

        setupClickListeners();
        setupToolbar();
        setupYouTubePlayer();
    }

    private void setupClickListeners() {
        binding.btnAddToFav.setOnClickListener(v -> handleFavoriteClick());
        binding.btnAddToPlanner.setOnClickListener(v -> handlePlannerClick());
    }

    private void handleFavoriteClick() {
        if (UserSessionManager.getInstance(requireContext()).isGuest()) {
            showGuestLoginDialog();
            return;
        }
        if (meal == null) return;

        if (isFavorite) {
            showRemoveFavoriteDialog();
        } else {
            updateFavoriteState(true);
            showFavoriteAdded();
            presenter.onFavoriteClicked(meal);
        }
    }

    private void showRemoveFavoriteDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Remove from Favorites")
                .setMessage("Are you sure you want to remove this meal from your favorites?")
                .setPositiveButton("Yes", (dialog, which) -> presenter.onFavoriteClicked(meal))
                .setNegativeButton("No", null)
                .show();
    }

    private void handlePlannerClick() {
        if (UserSessionManager.getInstance(requireContext()).isGuest()) {
            showGuestLoginDialog();
        } else if (meal != null) {
            binding.btnAddToPlanner.setColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.primary_500),
                    PorterDuff.Mode.SRC_IN
            );
            showDatePickerAndAddMeal();
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = binding.toolbar;
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
    }

    private void setupYouTubePlayer() {
        YouTubePlayerView youTubePlayerView = binding.youtubePlayerView;
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                if (videoId != null && !videoId.isEmpty()) {
                    youTubePlayer.cueVideo(videoId, 0);
                }
            }
        });
    }

    private void showGuestLoginDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Login Required")
                .setMessage("You need to login to use this feature. Would you like to login now?")
                .setPositiveButton("Login", (dialog, which) -> navigateToAuth())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void navigateToAuth() {
        Intent intent = new Intent(requireContext(), AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showLoading() {
        binding.loadingContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        binding.loadingContainer.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Constants.showErrorSnackbar(binding.getRoot(), message);
    }

    @Override
    public void showMealDetails(MealdetailsItemModel meal) {
        this.meal = meal;
        videoId = meal.getVideoId();
        binding.tvMealName.setText(meal.getName());
        binding.tvMealDetailsCategoryType.setText(meal.getCategory());
        binding.tvmealDetailsCountry.setText(meal.getArea());

        Glide.with(this).load(meal.getThumbNail()).into(binding.mealDetailsImage);
        Glide.with(this).load(meal.getCountryFlagUrl()).into(binding.mealCountryDetailsFlag);
    }

    @Override
    public void showIngredients(List<Ingredient> ingredients) {
        ingredientsAdapter = new IngredientsAdapter(this);
        binding.rvIngredients.setAdapter(ingredientsAdapter);
        binding.rvIngredients.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        ingredientsAdapter.setIngredients(ingredients);
    }

    @Override
    public void showInstructions(String instructions) {
        binding.tvInstructions.setText(instructions);
    }

    @Override
    public void updateFavoriteState(boolean isFavorite) {
        this.isFavorite = isFavorite;
        if (isFavorite) {
            binding.btnAddToFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.danger_700), PorterDuff.Mode.SRC_IN);
            animateHeart();
        } else {
            binding.btnAddToFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white), PorterDuff.Mode.SRC_IN);
        }
    }

    private void animateHeart() {
        binding.btnAddToFav.animate()
                .scaleX(1.2f).scaleY(1.2f).setDuration(150)
                .withEndAction(() -> binding.btnAddToFav.animate().scaleX(1.0f).scaleY(1.0f).setDuration(150).start());
    }

    private void showDatePickerAndAddMeal() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        Calendar startDate = (Calendar) today.clone();
        startDate.add(Calendar.DAY_OF_MONTH, 1);

        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.DAY_OF_MONTH, 6); // Tomorrow + 6 = 7 days total

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder()
                .setValidator(new CalendarConstraints.DateValidator() {
                    @Override
                    public boolean isValid(long date) {
                        return date >= startDate.getTimeInMillis() && date <= endDate.getTimeInMillis();
                    }
                    @Override public int describeContents() { return 0; }
                    @Override public void writeToParcel(android.os.Parcel dest, int flags) {}
                });

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select a date")
                .setCalendarConstraints(constraintsBuilder.build())
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(selection);
            String selectedDate = String.format("%04d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
            addMealToPlanner(selectedDate);
            binding.btnAddToPlanner.clearColorFilter();
        });

        datePicker.addOnNegativeButtonClickListener(v -> binding.btnAddToPlanner.clearColorFilter());
        datePicker.addOnCancelListener(v -> binding.btnAddToPlanner.clearColorFilter());
        datePicker.show(getParentFragmentManager(), "DATE_PICKER");
    }

    private void addMealToPlanner(String date) {
        if (presenter != null) {
            presenter.onAddToPlannerClicked(meal, date);
        }
    }

    @Override
    public void showFavoriteAdded() {
        Snackbar.make(binding.getRoot(), getString(R.string.added_to_favorites), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showFavoriteRemoved() {
        Snackbar.make(binding.getRoot(), getString(R.string.removed_from_favorites), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMealAddedToPlanner(String date) {
        Constants.showSuccessSnackbar(binding.getRoot(), getString(R.string.meal_added_to_planner_on) + date);
    }

    @Override
    public void onIngredientClick(Ingredient ingredient) {}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
