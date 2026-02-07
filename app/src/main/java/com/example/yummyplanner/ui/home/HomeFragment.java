package com.example.yummyplanner.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yummyplanner.data.model.Category;
import com.example.yummyplanner.data.model.Meal;
import com.example.yummyplanner.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private CategoryAdapter categoryAdapter;
    private MealAdapter mealAdapter;

    private List<Category> categoriesList = new ArrayList<>();
    private List<Meal> mealsList = new ArrayList<>();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        setupCategoriesRecycler();
        setupMealsRecycler();
    }

    // ================= Categories =================
    private void setupCategoriesRecycler() {

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false);

        binding.categoriesRecyclerView.setLayoutManager(layoutManager);
        binding.categoriesRecyclerView.setHasFixedSize(true);

        categoriesList.add(new Category(
                1,
                "Breakfast",
                "Breakfast",
                "https://www.themealdb.com/images/category/breakfast.png"
        ));
        categoriesList.add(new Category(
                2,
                "Seafood",
                "Seafood",
                "https://www.themealdb.com/images/category/seafood.png"
        ));
        categoriesList.add(new Category(

                3,
                "Dessert",
                "Dessert",
                "https://www.themealdb.com/images/category/dessert.png"
        ));

        categoryAdapter = new CategoryAdapter(categoriesList, category ->
                Toast.makeText(getContext(),
                        category.getName(),
                        Toast.LENGTH_SHORT).show()
        );

        binding.categoriesRecyclerView.setAdapter(categoryAdapter);
    }

    // ================= Meals =================
    private void setupMealsRecycler() {

        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(getContext(), 2);

        binding.popularMealsRecyclerView.setLayoutManager(gridLayoutManager);
        binding.popularMealsRecyclerView.setHasFixedSize(true);

        mealsList.add(createDummyMeal());
        mealsList.add(createDummyMeal());
        mealsList.add(createDummyMeal());
        mealsList.add(createDummyMeal());

        mealAdapter = new MealAdapter(mealsList, meal ->
                Toast.makeText(getContext(),
                        meal.getName(),
                        Toast.LENGTH_SHORT).show()
        );

        binding.popularMealsRecyclerView.setAdapter(mealAdapter);
    }

    private Meal createDummyMeal() {
        Meal meal = new Meal();
        meal.setName("Wontons");
        meal.setCategory("Pork");
        meal.setArea("Chinese");
        meal.setImageUrl("https://www.themealdb.com/images/media/meals/1525876468.jpg");

        return meal;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
