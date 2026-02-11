package com.example.yummyplanner.ui.details;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.yummyplanner.R;
import com.example.yummyplanner.databinding.FragmentDetailsBinding;
import com.example.yummyplanner.data.meals.model.MealdetailsItemModel;
import com.example.yummyplanner.data.meals.model.response.Ingredient;
import com.example.yummyplanner.ui.details.presenter.MealDetailsContract;
import com.example.yummyplanner.ui.details.presenter.MealDetailsPresenter;
import com.example.yummyplanner.utiles.Constants;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import java.util.List;

public class DetailsFragment extends Fragment implements MealDetailsContract.View , IngredientsAdapter.OnIngredientClickListener {

    private FragmentDetailsBinding binding;
    private MealDetailsContract.Presenter presenter;
    private IngredientsAdapter ingredientsAdapter;
    private  String videoId;
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

        presenter = new MealDetailsPresenter(this);

        presenter.getMealDetails(mealId);
        binding.btnAddToFav.setOnClickListener(v -> {
//            presenter.onFavoriteClicked();
        });

        binding.btnAddToPlanner.setOnClickListener(v -> {
//            presenter.addMealToPlanner();
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
            binding.btnAddToFav.setImageResource(
                    R.drawable.ic_heart
            );
        } else {
            binding.btnAddToFav.setImageResource(
                    R.drawable.add_to_favorite
            );
        }
    }

    @Override
    public void showFavoriteAdded() {
        Constants.showSuccessSnackbar(binding.getRoot(), "Added to favorites ❤️");
    }

    @Override
    public void showFavoriteRemoved() {
        Constants.showSuccessSnackbar(binding.getRoot(), "Removed from favorites 💔");
    }

    // ---------------- PLANNER ----------------

    @Override
    public void showMealAddedToPlanner(String date) {
        Constants.showSuccessSnackbar(
                binding.getRoot(),
                "Meal added to planner on " + date
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onIngredientClick(Ingredient ingredient) {

    }
}
