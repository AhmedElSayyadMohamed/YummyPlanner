package com.example.yummyplanner.ui.home;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.yummyplanner.R;
import com.example.yummyplanner.data.auth.repository.AuthRepositoryImpl;
import com.example.yummyplanner.data.meals.model.Area;
import com.example.yummyplanner.data.meals.model.Category;
import com.example.yummyplanner.data.meals.model.MealItemModel;
import com.example.yummyplanner.databinding.FragmentHomeBinding;
import com.example.yummyplanner.ui.home.adapter.CategoryAdapter;
import com.example.yummyplanner.ui.home.adapter.CuisinesAdapter;
import com.example.yummyplanner.ui.home.adapter.MealAdapter;
import com.example.yummyplanner.ui.home.presenter.HomeContract;
import com.example.yummyplanner.ui.home.presenter.HomePresenter;
import com.example.yummyplanner.utiles.Constants;


import java.util.List;


public class HomeFragment extends Fragment implements HomeContract.View,
        MealAdapter.OnMealClickListener,
        CategoryAdapter.OnCategoryClickListener,
        CuisinesAdapter.OnCuisineClickListener{

    private FragmentHomeBinding binding;
    private HomeContract.Presenter presenter;


    private CategoryAdapter categoriesAdapter;
    private MealAdapter popularAdapter;
    private CuisinesAdapter cuisinesAdapter;

    private  MealItemModel mealOfTheDay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HomePresenter(
                this,
                MealRepositoryImpl.getInstance(requireContext()),
                AuthRepositoryImpl.getInstance()
        );
    }

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


        initAdapter();
        setupClickListeners();
        binding.searchBarBtn.setOnClickListener(v -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_homeFragment_to_searchFragment);
        });
        presenter.attachView(this);
        presenter.loadHomeData();
    }

    private void initAdapter(){
        categoriesAdapter = new CategoryAdapter(this);
        binding.rvCategories.setAdapter(categoriesAdapter);
        binding.rvCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        popularAdapter = new MealAdapter(this);
        binding.rvPopularMeals.setAdapter(popularAdapter);
        binding.rvPopularMeals.setLayoutManager(new GridLayoutManager(getContext(), 2));


        cuisinesAdapter = new CuisinesAdapter(this);
        binding.rvArea.setAdapter(cuisinesAdapter);
        binding.rvArea.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

    }

    private void setupClickListeners() {
        binding.mealOfDayCard.setOnClickListener(v -> {
            if ( mealOfTheDay != null) {
                presenter.onMealOfTheDayClicked(mealOfTheDay);
            }
        });
    }

    @Override
    public void showLoading() {
         if (binding.loadingContainer != null && binding.loadingLottie != null) {
                binding.loadingContainer.setVisibility(View.VISIBLE);
                binding.loadingLottie.setVisibility(View.VISIBLE);
                binding.loadingLottie.playAnimation();
            }
    }

    @Override
    public void hideLoading() {
        if (binding.loadingContainer != null && binding.loadingLottie != null) {
            binding.loadingContainer.postDelayed(() -> {
                binding.loadingLottie.cancelAnimation();
                binding.loadingLottie.setVisibility(View.GONE);
                binding.loadingContainer.setVisibility(View.GONE);
            }, 2500);
        }
    }

    @Override
    public void showError(String message) {
        Constants.showErrorSnackbar(binding.getRoot(),message);
    }

    @Override
    public void showMealOfTheDay(MealItemModel meal) {
        if (!isAdded() || getView() == null) return;

        mealOfTheDay = meal;
        binding.tvMealOfTheDayTitle.setText(meal.getName());
        binding.tvMealOfTheDayCategory.setText(meal.getCategory());
        binding.mealOfTheDaymealCountryCountry.setText(meal.getArea());

        Glide.with(this)
                .load(meal.getCountryFlagUrl())
                .placeholder(R.drawable.flag)
                .into(binding.mealOfTheDaymealCountryFlag);

        Glide.with(this)
                .load(meal.getImageUrl())
                .placeholder(R.drawable.plan1)
                .into(binding.mealOfDayImage);
    }


    @Override
    public void showCategories(List<Category> categories) {
        if (!isAdded() || getView() == null) return;

        categoriesAdapter.setCategories(categories);

     }

    @Override
    public void showPopularMeals(List<MealItemModel> meals) {
        if (!isAdded() || getView() == null) return;
        popularAdapter.setMeals(meals);
    }

    @Override
    public void showCuisines(List<Area> areas) {

        if (!isAdded() || getView() == null) return;
        cuisinesAdapter.setCuisines(areas);

    }


    @Override
    public void navigateToMealDetails(MealItemModel meal) {
        if(!isAdded() || getView() == null|| meal == null) return;

        HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action =
                HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(meal.getId());

        NavHostFragment.findNavController(this).navigate(action);
    }

    @Override
    public void onMealClick(MealItemModel meal) {
        navigateToMealDetails(meal);
    }

    @Override
    public void navigateToMealsList(String filter, String type) {

    }

    @Override
    public void onCategoryClick(Category category) {

    }

    @Override
    public void onCuisineClicked(Area cuisine) {

    }

    @Override
    public void setUserName(String name) {
        if (!isAdded() || getView() == null) return;
          binding.tvUseName.setText(name);
    }


}
