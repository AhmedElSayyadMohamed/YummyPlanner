package com.example.yummyplanner.ui.details;

import android.app.DatePickerDialog;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.yummyplanner.R;
import com.example.yummyplanner.data.repository.MealRepositoryImpl;
import com.example.yummyplanner.databinding.FragmentDetailsBinding;
import com.example.yummyplanner.data.meals.model.MealdetailsItemModel;
import com.example.yummyplanner.data.meals.model.response.Ingredient;
import com.example.yummyplanner.ui.details.presenter.MealDetailsContract;
import com.example.yummyplanner.ui.details.presenter.MealDetailsPresenter;
import com.example.yummyplanner.utiles.Constants;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Calendar;
import java.util.List;

public class DetailsFragment extends Fragment implements MealDetailsContract.View , IngredientsAdapter.OnIngredientClickListener {

    private FragmentDetailsBinding binding;
    private MealDetailsContract.Presenter presenter;
    private IngredientsAdapter ingredientsAdapter;
    private  String videoId;
    private MealdetailsItemModel meal;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DetailsFragmentArgs args =
                DetailsFragmentArgs.fromBundle(getArguments());

        String mealId = args.getMealId();
        Log.d("DetailsFragment", "mealId: " + mealId);

        presenter = new MealDetailsPresenter(this, MealRepositoryImpl.getInstance(requireContext()));

        presenter.getMealDetails(mealId);

        binding.btnAddToFav.setOnClickListener(v -> {
            if (this.meal != null) {
                presenter.onFavoriteClicked(this.meal );
            }

        });

        binding.btnAddToPlanner.setOnClickListener(v -> {
            if (meal != null) {
                showDatePickerAndAddMeal();
            }
        });

        Toolbar toolbar = binding.toolbar;

        toolbar.setNavigationOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        YouTubePlayerView youTubePlayerView = binding.youtubePlayerView;
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                if (videoId != null && !videoId.isEmpty()) {
//                    youTubePlayer.loadVideo(videoId, 0f);
                    youTubePlayer.cueVideo(videoId, 0);
                    Log.d("YouTubePlayer", "Loading video: " + videoId);
                } else {
                    Log.w("YouTubePlayer", "Video ID is null, cannot load video.");
                }
            }
        });
    }


    @Override
    public void showLoading() {
        binding.loadingContainer.setVisibility(View.VISIBLE);
        binding.loadingLottie.setVisibility(View.VISIBLE);
        binding.loadingLottie.playAnimation();

        binding.appBar.setVisibility(View.GONE);
        binding.mealDetailsScrollView.setVisibility(View.GONE);
        binding.btnAddToPlanner.setVisibility(View.GONE);
        binding.btnAddToFav.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        binding.loadingLottie.cancelAnimation();
        binding.loadingContainer.setVisibility(View.GONE);

        binding.appBar.setVisibility(View.VISIBLE);
        binding.mealDetailsScrollView.setVisibility(View.VISIBLE);
        binding.btnAddToPlanner.setVisibility(View.VISIBLE);
        binding.btnAddToFav.setVisibility(View.VISIBLE);
    }


    @Override
    public void showError(String message) {
        Constants.showErrorSnackbar(binding.getRoot(), message);
    }


    @Override
    public void showMealDetails(MealdetailsItemModel meal) {
        if(meal!=null){
            this.meal = meal;
        }
        videoId = meal.getVideoId();
        binding.tvMealName.setText(meal.getName());
        binding.tvMealDetailsCategoryType.setText(meal.getCategory());
        binding.tvmealDetailsCountry.setText(meal.getArea());

        if (ingredientsAdapter != null) {
            ingredientsAdapter.setIngredients(meal.getIngredientsList());
        }

        Glide.with(this)
                .load(meal.getThumbNail())
                .into(binding.mealDetailsImage);

        Glide.with(this)
                .load(meal.getCountryFlagUrl())
                .into(binding.mealCountryDetailsFlag);
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
        if (isFavorite) {
            binding.btnAddToFav.setColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.danger_700),
                    PorterDuff.Mode.SRC_IN
            );
            animateHeart();
        } else {
            binding.btnAddToFav.setColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.white),
                    PorterDuff.Mode.SRC_IN
            );
        }
    }

    private void animateHeart() {
        binding.btnAddToFav.setScaleX(0f);
        binding.btnAddToFav.setScaleY(0f);

        binding.btnAddToFav.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(400)
                .setInterpolator(new OvershootInterpolator());
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
        endDate.add(Calendar.DAY_OF_MONTH, 7);

        CalendarConstraints.DateValidator validator =
                DateValidatorPointForward.from(startDate.getTimeInMillis());

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder()
                .setValidator(new CalendarConstraints.DateValidator() {
                    @Override
                    public boolean isValid(long date) {
                        return date >= startDate.getTimeInMillis() && date <= endDate.getTimeInMillis();
                    }

                    @Override
                    public int describeContents() { return 0; }

                    @Override
                    public void writeToParcel(android.os.Parcel dest, int flags) {}
                });

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select a date")
                .setCalendarConstraints(constraintsBuilder.build())
                .setTheme(com.google.android.material.R.style.ThemeOverlay_Material3_MaterialCalendar)
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(selection);

            String selectedDate = String.format("%04d-%02d-%02d",
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.DAY_OF_MONTH));

            addMealToPlanner(selectedDate);
        });

        datePicker.show(getParentFragmentManager(), "DATE_PICKER");
    }

    private void addMealToPlanner(String date) {
        if (presenter != null) {
            presenter.onAddToPlannerClicked(meal, date);
        }
    }


    @Override
    public void showFavoriteAdded() {
        Constants.showSuccessSnackbar(binding.getRoot(), getString(R.string.added_to_favorites));
    }

    @Override
    public void showFavoriteRemoved() {
        Constants.showSuccessSnackbar(binding.getRoot(), getString(R.string.removed_from_favorites));
    }

    @Override
    public void showMealAddedToPlanner(String date) {
        Constants.showSuccessSnackbar(
                binding.getRoot(),
                getString(R.string.meal_added_to_planner_on) + date
        );
    }

    @Override
    public void onIngredientClick(Ingredient ingredient) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
