package com.example.yummyplanner.ui.home;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummyplanner.R;
import com.example.yummyplanner.data.model.Category;
import com.example.yummyplanner.data.model.Meal;
import com.example.yummyplanner.databinding.FragmentHomeBinding;
import com.example.yummyplanner.ui.home.presenter.HomeContract;
import com.example.yummyplanner.ui.home.presenter.HomePresenter;


import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View, CategoryAdapter.OnCategoryClickListener, MealAdapter.OnMealClickListener {

    private FragmentHomeBinding binding;
    private HomeContract.Presenter presenter;
    private CategoryAdapter categoriesAdapter;
    private MealAdapter popularAdapter;


    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        presenter = new HomePresenter(this);
        setupRecyclerViews();

        showLoading();
        presenter.getMealOfTheDay();
        presenter.getCategories();
        presenter.getPopularMeals();
    }


    private void setupRecyclerViews() {
        binding.categoriesRecyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        categoriesAdapter = new CategoryAdapter(this);
        binding.categoriesRecyclerView.setAdapter(categoriesAdapter);

        // Popular Meals RecyclerView
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(requireContext(), 2);

        binding.popularMealsRecyclerView.setLayoutManager(gridLayoutManager);

        popularAdapter = new MealAdapter(this);
        binding.popularMealsRecyclerView.setAdapter(popularAdapter);
    }

    @Override
    public void showLoading() {
        binding.loadingContainer.setVisibility(View.VISIBLE);
        binding.contentContainer.setVisibility(View.GONE);
        binding.loadingLottie.playAnimation();
    }

    @Override
    public void hideLoading() {
        binding.loadingLottie.cancelAnimation();
        binding.loadingContainer.setVisibility(View.GONE);
        binding.contentContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMealOfTheDay(Meal meal) {
        hideLoading();

        binding.tvTitle.setText(meal.getName());
        binding.tvDescription.setText(
                meal.getCategory() + " • " + meal.getArea()
        );

        Glide.with(this)
                .load(meal.getImageUrl())
                .placeholder(R.drawable.plan1)
                .error(R.drawable.plan1)
                .into(binding.mealOfDayImage);
    }

    @Override
    public void showCategories(List<Category> categories) {
        if (categoriesAdapter != null) {
            categoriesAdapter.setCategories(categories);
        }
    }

    @Override
    public void showPopularMeals(List<Meal> meals) {
        if (popularAdapter != null) {
            popularAdapter.setMeals(meals);
        }
    }

    @Override
    public void showError(String message) {
        hideLoading();
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoInternet() {
        hideLoading();
        Toast.makeText(requireContext(),
                "No Internet Connection",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryClick(Category category) {
        // Handle category click
        Toast.makeText(getContext(), "Clicked: " + category.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMealClick(Meal meal) {
        // Handle meal click
        Toast.makeText(getContext(), "Clicked: " + meal.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        if (presenter != null) {
            presenter.onDestroy();
        }
        binding = null;
        super.onDestroyView();
    }
}
